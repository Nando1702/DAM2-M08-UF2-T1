package com.example.dam2_m08_uf2_t1.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.VolleyError;
import com.example.dam2_m08_uf2_t1.R;
import com.example.dam2_m08_uf2_t1.apis.ApiClientInfoEstacions;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;

import com.example.dam2_m08_uf2_t1.apis.ApiClientEstatEstacions;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;
import com.example.dam2_m08_uf2_t1.recyclerView.Adaptador;
import com.example.dam2_m08_uf2_t1.recyclerView.RecyclerViewInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private MapView mapa;
    private MapController mapController;
    private Context contexto;
    private RecyclerView rv;
    private Adaptador adaptador;
    private boolean isMap;
    private static int mode = 0;
    private static final int MODE_MEZCLADO = 0;
    private static final int MODE_ABIERTO = 1;
    private static final int MODE_FAVORITAS = 2;
    private boolean filtDistancia = false;
    private MyLocationNewOverlay myLocationOverlay;
    private ArrayList<Estacion> estacionBicings;
    private ArrayList<Estacion> auxEstacion;
    private double longitudActual;
    private double latitudActual ;
    private double zoomMapaActual;
    private MenuItem m1;
    private MenuItem m5;
    private boolean arrayLleno = false;
    private Set<Integer> ubicacionesFavoritasId;
    private float maxDistance = 1000000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Inicializar osmdroid
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, getPreferences(Context.MODE_PRIVATE));
        // Configurar el directorio de caché (opcional)
        Configuration.getInstance().setOsmdroidTileCache(getCacheDir());
        // Configurar otros ajustes de osmdroid si es necesario


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mapa = this.findViewById(R.id.mapa);
        this.rv = this.findViewById(R.id.recyclerView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            iniciarMapaConUbicacionActual();
        }

        FusedLocationProviderClient fps = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        if (savedInstanceState == null) {
            longitudActual = 2.1686;
            latitudActual = 41.3874;
            zoomMapaActual=18;
            obtenerEstatEstacions();
            isMap = true;
            ubicacionesFavoritasId = SharedPref.getFavoriteLocationsInt(getApplicationContext());
            cargarMapa();
        }
        this.adaptador = new Adaptador(this, estacionBicings, this);//hay q cambiar cosas en el adaptador
        rv.setAdapter(adaptador);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapa.onPause();  // Pausar el mapa para liberar recursos
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapa.onResume();  // Reanudar el mapa cuando la actividad se reanuda
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        latitudLongitudActual();
        outState.putBoolean("isMap", isMap);
        outState.putParcelableArrayList("listaEstaciones", estacionBicings);
        outState.putInt("mode", mode);
        outState.putBoolean("isFiltDistance", filtDistancia);
        outState.putFloat("distancia", maxDistance);
        outState.putParcelableArrayList("listauxEstaciones", auxEstacion);
        outState.putDouble("latitudActual", latitudActual);
        outState.putDouble("longitudActual", longitudActual);
        outState.putDouble("zoomMapaActual", zoomMapaActual);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latitudActual = savedInstanceState.getDouble("latitudActual");
        longitudActual = savedInstanceState.getDouble("longitudActual");
        zoomMapaActual = savedInstanceState.getDouble("zoomMapaActual");
        cargarMapa();
        isMap = !savedInstanceState.getBoolean("isMap");
        auxEstacion = savedInstanceState.getParcelableArrayList("listauxEstaciones");
        estacionBicings = savedInstanceState.getParcelableArrayList("listaEstaciones");
        if (auxEstacion == null) {
            auxEstacion = estacionBicings;
        }
        mode = savedInstanceState.getInt("mode");
        filtDistancia = savedInstanceState.getBoolean("isFiltDistance");
        maxDistance = savedInstanceState.getFloat("distancia");
        cambiarListaMap();
        crearMarcas(auxEstacion);
        adaptador.setListaEstaciones(auxEstacion);

    }

    private void cargarMapa() {
        this.mapa.setTileSource(TileSourceFactory.MAPNIK);
        this.mapController = (MapController) this.mapa.getController();
        this.mapController.setZoom(zoomMapaActual);
        this.mapController.setCenter(new GeoPoint(latitudActual, longitudActual));
    }
    private void latitudLongitudActual() {
        GeoPoint centroMapa = (GeoPoint) mapa.getMapCenter();


        this.zoomMapaActual = mapa.getZoomLevelDouble();
        this.latitudActual = centroMapa.getLatitude();
        this.longitudActual = centroMapa.getLongitude();
    }
    private void iniciarMapaConUbicacionActual() {
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapa);
        myLocationOverlay.enableMyLocation();
        mapa.getOverlays().add(myLocationOverlay);
        GeoPoint startPoint = myLocationOverlay.getMyLocation();

        if (startPoint != null) {
            mapController.setCenter(startPoint);
        }
    }

    private void obtenerEstatEstacions() {
        ApiClientEstatEstacions.obtenerDatosEstatEstacions(getApplicationContext(), new ApiClientEstatEstacions.OnDataFetchedListener() {
            @Override
            public void onSuccess(ArrayList<Estacion> estacionEstat) {
                // Ahora puedes usar el array de estaciones en tu actividad principal
                //estacionBicings = estacionEstat;
                obtenerinfoEstacions(estacionEstat);
                //llamar a la api client info
            }

            @Override
            public void onError(VolleyError error) {
            }
        });
    }

    private void obtenerinfoEstacions(ArrayList<Estacion> estacionEstat) {
        ApiClientInfoEstacions.obtenerDatosinfoEstacions(getApplicationContext(), estacionEstat, new ApiClientInfoEstacions.OnDataFetchedListener() {
            @Override
            public void onSuccess(ArrayList<Estacion> estacionesInfo) {
                // Manipular la lista estacionBicings dentro de este bloque synchronized
                estacionBicings = estacionesInfo;
                adaptador.setListaEstaciones(estacionBicings);
                arrayLleno = true;
                auxEstacion = estacionBicings;
                getfavoritesPref();
                crearMarcas(estacionBicings);
            }

            @Override
            public void onError(VolleyError error) {
                // Manejar el error si es necesario
                System.out.println(error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        m1 = menu.findItem(R.id.item1);
        m5 = menu.findItem(R.id.item5);
        m5.setChecked(filtDistancia);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        auxEstacion = new ArrayList<>();
        int num = item.getItemId();

        if (num == R.id.item1) {
            cambiarListaMap();
        } else if (num == R.id.item2) {

            if (mode != MODE_ABIERTO) {
                mode = MODE_ABIERTO;
                borrarMarcadoresMapa();
                auxEstacionAbiertos();

                if (filtDistancia) {
                    filtrarDistanciaAuxEstacion(auxEstacion);
                }
                adaptador.setListaEstaciones(auxEstacion);
                crearMarcas(auxEstacion);
            }
        } else if (num == R.id.item3) {

            if (mode != MODE_FAVORITAS) {
                mode = MODE_FAVORITAS;
                borrarMarcadoresMapa();

                auxEstacionFaboritos();

                if (filtDistancia) {
                    filtrarDistanciaAuxEstacion(auxEstacion);
                }
                adaptador.setListaEstaciones(auxEstacion);
                crearMarcas(auxEstacion);
                adaptador.setListaEstaciones(auxEstacion);
            }
        } else if (num == R.id.item4) {

            if (mode != MODE_MEZCLADO) {
                auxEstacion = estacionBicings;
                mode = MODE_MEZCLADO;
                borrarMarcadoresMapa();

                if (filtDistancia) {
                    filtrarDistanciaAuxEstacion(auxEstacion);
                }
                crearMarcas(estacionBicings);
                adaptador.setListaEstaciones(estacionBicings);
            }
        } else if (num == R.id.item5) {
            filtDistancia = !filtDistancia;
            m5.setChecked(filtDistancia);
            borrarMarcadoresMapa();

            if (filtDistancia) {

                if (auxEstacion == null) {
                    auxEstacion = estacionBicings;
                }
                filtrarDistanciaAuxEstacion(estacionBicings);
            } else {

                if (mode == MODE_MEZCLADO) {
                    auxEstacion = estacionBicings;
                } else if (mode == MODE_FAVORITAS) {
                    auxEstacionFaboritos();
                } else if (mode == MODE_ABIERTO) {
                    auxEstacionAbiertos();
                }
            }
            crearMarcas(auxEstacion);
            adaptador.setListaEstaciones(auxEstacion);
        } else if (num == R.id.item6) {
            mostrarDialogoDistanciaMaxima();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void filtrarDistanciaAuxEstacion(ArrayList<Estacion> estacions) {
        ArrayList<Estacion> aux = new ArrayList<>();
        if (myLocationOverlay != null && myLocationOverlay.getMyLocation() != null) {
            double lat = myLocationOverlay.getMyLocation().getLatitude();
            double lon = myLocationOverlay.getMyLocation().getLongitude();
            for (Estacion est : estacions) {
                if (calcularDistancia(lat, lon, est.getLat(), est.getLon()) < maxDistance) {
                    aux.add(est);
                }
            }
        }
        auxEstacion.clear();
        auxEstacion.addAll(aux);
    }
    private void auxEstacionAbiertos() {
        auxEstacion.clear();

        for (Estacion est : estacionBicings) {

            if (est.getStatus().equals("IN_SERVICE")) {
                auxEstacion.add(est);
            }
        }
    }

    private void mostrarDialogoDistanciaMaxima() {
        // Crear un EditText para que el usuario ingrese la distancia máxima
        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER); // Asegurar que solo se permitan números
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Configurar distancia máxima (metros)");
        builder.setView(input);
        // Agregar botones "Aceptar" y "Cancelar" al cuadro de diálogo
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString();
                if (!inputText.isEmpty()) {
                    // Actualizar la distancia máxima con el valor ingresado por el usuario
                    maxDistance = Integer.parseInt(inputText);
                    // Aquí puedes utilizar el valor de maxDistance para filtrar las ubicaciones cercanas al usuario


                    if (filtDistancia) {

                        if (auxEstacion == null) {
                            auxEstacion = estacionBicings;
                        }

                        borrarMarcadoresMapa();
                        filtrarDistanciaAuxEstacion(estacionBicings);
                        crearMarcas(auxEstacion);
                        adaptador.setListaEstaciones(auxEstacion);
                    }


                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Mostrar el cuadro de diálogo
        builder.show();
    }

    public void botonListMap(View view) {
        cambiarListaMap();
    }

    private void auxEstacionFaboritos() {
        auxEstacion.clear();
        for (Estacion est : estacionBicings) {

            if (est.isFavorite()) {
                auxEstacion.add(est);
            }
        }
    }

    private void cambiarListaMap() {
        FloatingActionButton button = findViewById(R.id.floating_button);
        this.isMap = !this.isMap;

        if (isMap) {
            mapa.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
            button.setImageResource(R.drawable.baseline_menu_24);
        } else {
            mapa.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            button.setImageResource(R.drawable.baseline_map_24);
        }
        if (m1 != null) {
            m1.setTitle(isMap ? "Lista" : "Mapa");
        }
    }

    @Override
    public void onItemCLick(int position) {
        System.out.println("-------------estacionCercana1----------");
        String estacionCercana = NomEstacionMasCercana(auxEstacion.get(position));
        System.out.println(estacionCercana);
        Intent intent = new Intent(MainActivity.this, DetalleEstacionActivity.class);
        intent.putExtra("estacionMasCercana", estacionCercana);
        intent.putExtra("estacion", (Serializable) auxEstacion.get(position));
        startActivity(intent);
    }

    @Override
    public void onButtonCLick(int position) {
       Estacion estacion = auxEstacion.get(position);
        estacion.setFavorite(!estacion.isFavorite());
        adaptador.setListaEstaciones(auxEstacion);
        if (mode == MODE_FAVORITAS){
            auxEstacionFaboritos();
            adaptador.setListaEstaciones(auxEstacion);
        }
        borrarMarcadoresMapa();
        crearMarcas(auxEstacion);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            iniciarMapaConUbicacionActual();
        }
    }

    public void centrarMapa(View view) {
        GeoPoint myLocation = myLocationOverlay.getMyLocation();
        if (myLocation != null) {
            mapController.setCenter(myLocation);
        }
    }

    private void addStationMarker(Estacion estacion) {
        Marker stationMarker = new Marker(mapa);
        stationMarker.setPosition(new GeoPoint(estacion.getLat(), estacion.getLon()));
        colorMarcaEstacion(estacion, stationMarker);
        mapa.getOverlays().add(stationMarker);
        Marker finalStationMarker = stationMarker;
        stationMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                // Obtener la posición de la estación
                GeoPoint stationPosition = marker.getPosition();
                // Centrar el mapa en la posición de la estación con un zoom específico
                mapController.animateTo(stationPosition, 18.0, 1000L);
                // Mostrar detalles de la estación en el mapa
                showStationDetails(estacion, finalStationMarker);
                return true;
            }
        });
    }

    private void showStationDetails(Estacion estacion, Marker stationMarker) {
        // Crear un cuadro de diálogo o vista emergente para mostrar los detalles de la estación
        // Aquí puedes utilizar un cuadro de diálogo personalizado o una vista emergente según tus necesidades
        // Debes mostrar: tipo de estación, bicis disponibles, estado (abierto/cerrado), dirección
        // También incluir un botón que lleve a un nuevo activity con información completa
        // Ejemplo de cuadro de diálogo simple
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Detalles de la estación");
        builder.setMessage("Nombre de la estación: " + estacion.getName() + "\n"
                + "Bicis electronicas disponibles: " + estacion.getBikes_ebike() + "\n"
                + "Bicis mecanicas disponibles: " + estacion.getBikes_mechanical() + "\n"
                + "Estado: " + (estacion.getStatus().equals("IN_SERVICE") ? "Abierto" : "Cerrado") + "\n"
                + "Dirección: " + estacion.getAddress());

        // Botón para ir a un nuevo activity
        builder.setPositiveButton("Ver más", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Abrir la actividad DetalleEstacionActivity con información completa
                System.out.println("-------------estacionCercana2----------");
                String estacionCercana = NomEstacionMasCercana(estacion);
                System.out.println(estacionCercana);
                Intent intent = new Intent(MainActivity.this, DetalleEstacionActivity.class);
                intent.putExtra("estacionMasCercana", estacionCercana);
                intent.putExtra("estacion", (Serializable) estacion);
                startActivity(intent);
            }
        });

        // Botón para cerrar el cuadro de diálogo
        builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton((estacion.isFavorite() != true ? "✩" : "★"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Abrir la actividad DetalleEstacionActivity con información completa
                estacion.setFavorite(!estacion.isFavorite());
                colorMarcaEstacion(estacion, stationMarker);
                if (mode == MODE_FAVORITAS){
                    auxEstacionFaboritos();
                    adaptador.setListaEstaciones(auxEstacion);
                }
                borrarMarcadoresMapa();
                crearMarcas(auxEstacion);
            }
        });

        // Mostrar el cuadro de diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void colorMarcaEstacion(Estacion estacion, Marker stationMarker) {
        if (estacion.isFavorite()) {
            SharedPref.addFavoriteLocation(getApplicationContext(), estacion.getStationId());
            stationMarker.setIcon(getResources().getDrawable(R.drawable.marcaamarilla)); // Update marker icon for favorite station
        } else {
            SharedPref.removeFavoriteLocation(getApplicationContext(), estacion.getStationId()); // Remove from favorites if no longer favorite

            if (estacion.getStatus().equals("IN_SERVICE")) {
                stationMarker.setIcon(getResources().getDrawable(R.drawable.marcaroja)); // Update marker icon for open station
            } else {
                stationMarker.setIcon(getResources().getDrawable(R.drawable.marcanegra)); // Update marker icon for closed station
            }
        }
        // Update marker icon on the map
        mapa.invalidate();
    }
    private void borrarMarcadoresMapa() {

        latitudLongitudActual();

        mapa.getOverlays().clear();
        mapa.invalidate();

        // Vuelve a agregar el marcador de ubicación actual
        if (myLocationOverlay != null) {
            mapa.getOverlays().add(myLocationOverlay);
        }
        cargarMapa();
    }

    private void crearMarcas(ArrayList<Estacion> estacions) {
        mapa.getOverlays().clear();

        for (Estacion est : estacions) {
            addStationMarker(est);
        }
        if (myLocationOverlay != null) {
            mapa.getOverlays().add(myLocationOverlay);
        }
        mapa.invalidate(); // Invalidate the map view to refresh
    }
    private void getfavoritesPref() {
        for (Estacion est : estacionBicings) {
            if (ubicacionesFavoritasId.contains(est.getStationId())) {
                est.setFavorite(true);
                System.out.println(est);
            } else {
                est.setFavorite(false);
            }
        }
    }

    public static float calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        Location location1 = new Location("");
        location1.setLatitude(lat1);
        location1.setLongitude(lon1);

        Location location2 = new Location("");
        location2.setLatitude(lat2);
        location2.setLongitude(lon2);

        //System.out.println(location1.distanceTo(location2));

        return location1.distanceTo(location2);
    }
    private String NomEstacionMasCercana(Estacion est) {
        float distanciaAux = Float.MAX_VALUE; // Inicializa distanciaAux con un valor máximo
        String nombreEstacionMasCercana = null;

        for (Estacion estacion : estacionBicings) {
            if (estacion.getStationId() != est.getStationId()) {
                float distancia = calcularDistancia(est.getLon(), est.getLat(), estacion.getLon(), estacion.getLat());
                if (distancia < distanciaAux) {
                    distanciaAux = distancia;
                    nombreEstacionMasCercana = estacion.getName();
                }
            }
        }
        return nombreEstacionMasCercana;
    }
}
