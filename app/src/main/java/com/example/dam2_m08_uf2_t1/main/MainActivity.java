package com.example.dam2_m08_uf2_t1.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dam2_m08_uf2_t1.R;
import com.example.dam2_m08_uf2_t1.apis.ApiClientEstatEstacions;
import com.example.dam2_m08_uf2_t1.modelo.EstacionEstat;
import com.example.dam2_m08_uf2_t1.recyclerView.Adaptador;
import com.example.dam2_m08_uf2_t1.recyclerView.RecyclerViewInterface;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
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
    private MyLocationNewOverlay myLocationOverlay;
    private ArrayList<EstacionEstat> estacionBicings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("hola");
        EEsTADO();
        isMap = true;

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

        this.adaptador = new Adaptador(this, /*EEsTADO()*/null ,this);
        //Toast.makeText(ctx, EEsTADO().toString(), Toast.LENGTH_SHORT).show();


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
        ApiClientEstatEstacions.obtenerDatosEstatEstacions(new ApiClientEstatEstacions.OnDataFetchedListener() {


            @Override
            public void onDataFetched(String data) {

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
        GeoPoint startPoint = myLocationOverlay.getMyLocation();

        if (startPoint != null) {
            mapController.setCenter(startPoint);
        }
    }
}


