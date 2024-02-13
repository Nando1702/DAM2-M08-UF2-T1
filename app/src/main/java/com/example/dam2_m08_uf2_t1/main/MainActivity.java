package com.example.dam2_m08_uf2_t1.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.dam2_m08_uf2_t1.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;

public class MainActivity extends AppCompatActivity {

    private MapView mapa;
    private MapController mapController;
    private Context contexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.mapa = this.findViewById(R.id.mapa);
        this.mapController = (MapController) this.mapa.getController();
        // Aplicar zoom al mapa
        this.mapController.setZoom(18);

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
}