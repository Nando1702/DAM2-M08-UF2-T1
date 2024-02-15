package com.example.dam2_m08_uf2_t1.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.example.dam2_m08_uf2_t1.R;
import com.example.dam2_m08_uf2_t1.modelo.EstacionBicing;
import com.example.dam2_m08_uf2_t1.recyclerView.Adaptador;
import com.example.dam2_m08_uf2_t1.recyclerView.RecyclerViewInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    private MapView mapa;
    private MapController mapController;
    private Context contexto;
    private RecyclerView rv;
    private Adaptador adaptador;
    private boolean isMap;
    private ArrayList<EstacionBicing> estacionBicings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        this.adaptador = new Adaptador(this,this.estacionBicings,this);
        this.mapa.setTileSource(TileSourceFactory.MAPNIK);
        // Establecer el proveedor de mapas (ejemplo: MAPNIK)
        this.mapController = (MapController) this.mapa.getController();
        // Aplicar zoom al mapa
        this.mapController.setZoom(18);
        // Establecer la posición inicial del mapa (ejemplo: Barcelona)
        this.mapController.setCenter(new GeoPoint(41.3851, 2.1734));

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
}