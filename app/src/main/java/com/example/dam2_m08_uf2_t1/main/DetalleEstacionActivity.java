package com.example.dam2_m08_uf2_t1.main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.dam2_m08_uf2_t1.R;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;

public class DetalleEstacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        Intent intent = getIntent();
        Estacion estacion = (Estacion) intent.getSerializableExtra("estacion");
        String estacionMasCercana = (String) intent.getSerializableExtra("estacionMasCercana");
        System.out.println(estacionMasCercana);
        // Asignar valores a los TextViews
        TextView IdEstacion = findViewById(R.id.textIdEstacion);
        IdEstacion.setText("Id de laestación: " + estacion.getStationId());

        TextView nombreEstacion = findViewById(R.id.textNombreEstacion);
        nombreEstacion.setText("Nombre de la estación: " + estacion.getName());

        TextView direccion = findViewById(R.id.textDireccion);
        direccion.setText("Dirección: " + estacion.getAddress());

        TextView bicisDisponibles = findViewById(R.id.textBicisDisponibles);
        bicisDisponibles.setText("Bicis disponibles: " + estacion.getNum_bikes_available());

        TextView latitud = findViewById(R.id.textLatitud);
        latitud.setText("Latitud: " + estacion.getLat());

        TextView longitud = findViewById(R.id.textLongitud);
        longitud.setText("Longitud: " + estacion.getLon());

        TextView bikes_mechanical = findViewById(R.id.textBikes_mechanical);
        bikes_mechanical.setText("Bicis mecanicas disponibles: " + estacion.getBikes_mechanical());

        TextView bikes_ebike = findViewById(R.id.textBikes_ebike);
        bikes_ebike.setText("Bicis electricas disponibles: " + estacion.getBikes_ebike());

        TextView num_docks_available = findViewById(R.id.textNum_docks_available);
        num_docks_available.setText("Número de muelles disponibles: " + estacion.getNum_docks_available());

        TextView last_reported = findViewById(R.id.textLast_reported);
        last_reported.setText("último reportado: " + estacion.getLast_reported());

        TextView is_charging_station = findViewById(R.id.textIs_charging_station);
        is_charging_station.setText("Estación de carga: " + estacion.isIs_charging_station());

        TextView status = findViewById(R.id.textStatus);
        status.setText("Estado: " + estacion.getStatus());

        TextView physicalConfiguration = findViewById(R.id.textPhysicalConfiguration);
        physicalConfiguration.setText("Configuración física: " + estacion.getPhysicalConfiguration());

        TextView postCode = findViewById(R.id.textPostCode);
        postCode.setText("Codigo postal: " + estacion.getPostCode());

        TextView altitude = findViewById(R.id.textAltitude);
        altitude.setText("Altitud: " + estacion.getAltitude());

        TextView isFavorite = findViewById(R.id.textIsFavorite);
        isFavorite.setText("Favorita " + estacion.isFavorite() + ": " + (estacion.isFavorite()!=true? "✩" : "★"));

        TextView capacity = findViewById(R.id.textCapacity);
        capacity.setText("Dirección: " + estacion.getCapacity());

        TextView rideCodeSupport = findViewById(R.id.textRideCodeSupport);
        rideCodeSupport.setText("Soporte de código de viaje: " + estacion.isRideCodeSupport());

        TextView estacionMasCercanaNom = findViewById(R.id.textestacionMasCercana);
        estacionMasCercanaNom.setText("Estación Cercana: " + estacionMasCercana);

        Button closeButton = findViewById(R.id.button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Cierra la actividad actual
            }
        });
    }
}
