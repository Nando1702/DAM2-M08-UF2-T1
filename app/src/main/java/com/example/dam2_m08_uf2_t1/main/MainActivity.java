package com.example.dam2_m08_uf2_t1.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
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
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

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

    private static final int MODE_CERRADAS = 2;

    private static final int MODE_FAVORITAS = 3;

    private MyLocationNewOverlay myLocationOverlay;
    private ArrayList<Estacion> estacionBicings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("hola");
        EEsTADO();
        isMap = true;


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        // Inicializar osmdroid
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, getPreferences(Context.MODE_PRIVATE));
        // Configurar el directorio de caché (opcional)
        Configuration.getInstance().setOsmdroidTileCache(getCacheDir());
        // Configurar otros ajustes de osmdroid si es necesario

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mapa = this.findViewById(R.id.mapa);
        this.rv = this.findViewById(R.id.recyclerView);

        //this.adaptador = new Adaptador(this, /*EEsTADO()*/null ,this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            iniciarMapaConUbicacionActual();
        }

        FusedLocationProviderClient  fps = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);


        this.mapa.setTileSource(TileSourceFactory.MAPNIK);
        // Establecer el proveedor de mapas (ejemplo: MAPNIK)
        this.mapController = (MapController) this.mapa.getController();
        // Aplicar zoom al mapa
        this.mapController.setZoom(18);
        // Establecer la posición inicial del mapa (ejemplo: Barcelona)
        this.mapController.setCenter(new GeoPoint(41.3851, 2.1734));

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

    private void EEsTADO(){
        ApiClientEstatEstacions.obtenerDatosEstatEstacions(getApplicationContext(), new ApiClientEstatEstacions.OnDataFetchedListener() {
            @Override
            public void onSuccess(ArrayList<Estacion> estacionEStat) {
                // Ahora puedes usar el array de estaciones en tu actividad principal
                estacionBicings = estacionEStat;
                //llamar a la api client info
            }

            @Override
            public void onError(VolleyError error) {
                // Handle error
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int num = item.getItemId();

        if (num == R.id.item1) {



        } else if (num == R.id.item2) {



        } else if (num == R.id.item3) {



        } else if (num == R.id.item4) {



        } else {

            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void botonListMap(View view){

        cambiarListaMap(view);

    }
    private void cambiarListaMap(View view) {
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
    }
    @Override
    public void onItemCLick(int position) {

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

    private void addStationMarker(double latitude, double longitude, boolean isOpen) {
        Marker stationMarker = new Marker(mapa);
        stationMarker.setPosition(new GeoPoint(latitude, longitude));

        // Seleccionar el drawable dependiendo de si la estación está abierta o cerrada
        if (isOpen) {
            stationMarker.setIcon(getResources().getDrawable(R.drawable.marcaroja)); // Reemplaza con el drawable para estación abierta
        } else {
            stationMarker.setIcon(getResources().getDrawable(R.drawable.marcanegra)); // Reemplaza con el drawable para estación cerrada
        }

        mapa.getOverlays().add(stationMarker);

        stationMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                // Crear un Intent para abrir la actividad DetalleEstacionActivity
                Intent intent = new Intent(MainActivity.this, Ubicacion.class);

                // Agregar cualquier dato adicional que desees pasar a la actividad
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("isOpen", isOpen);

                // Iniciar la actividad
                startActivity(intent);

                return true; // Devuelve true para indicar que el evento de clic ha sido manejado
            }
        });
    }

}


