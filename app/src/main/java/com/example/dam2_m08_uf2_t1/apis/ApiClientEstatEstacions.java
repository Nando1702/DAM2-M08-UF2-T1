package com.example.dam2_m08_uf2_t1.apis;

import android.util.Log;

import com.example.dam2_m08_uf2_t1.modelo.EstacionEstat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClientEstatEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/";
    private static final String DATASET_ID = "bd2462df-6e1e-4e37-8205-a4b8e7313b84";  // Reemplaza con el identificador real del conjunto de datos
    private static final String RESOURCE_ID = "f60e9291-5aaa-417d-9b91-612a9de800aa";  // Reemplaza con el identificador real del recurso
    private static final String TOKEN = "4426b8c0c77727dfe8f453247a9ede96286a9dfdfb6cbbc98c43d1ed409a5dde";

    public interface OnDataFetchedListener {
        void onDataFetched(String data);
    }

    public static void obtenerDatosEstatEstacions(OnDataFetchedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                String url = BASE_URL + DATASET_ID + "/resource/" + RESOURCE_ID + "/download/recurs.json";
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", TOKEN)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String data = response.body().string();
                        if (listener != null) {
                            listener.onDataFetched(data);
                        }
                    } else {
                        // Manejar error
                        Log.e("Error", "Error en la solicitud: " + response.code());
                    }
                } catch (IOException e) {
                    // Manejar excepción
                    e.printStackTrace();
                    Log.e("Error", "Excepción durante la solicitud: " + e.getMessage());
                }
            }
        }).start();
    }



    private static void procesarInformacion(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray stations = data.getJSONArray("stations");

            for (int i = 0; i < stations.length(); i++) {
                JSONObject station = stations.getJSONObject(i);
                int stationId = station.getInt("station_id");
                int bikesAvailable = station.getInt("num_bikes_available");
                int mechanicalBikes = station.getJSONObject("num_bikes_available_types").getInt("mechanical");
                int ebikes = station.getJSONObject("num_bikes_available_types").getInt("ebike");
                int docksAvailable = station.getInt("num_docks_available");
                String status = station.getString("status");

                // Aquí puedes hacer lo que desees con la información de cada estación
                System.out.println("Estación " + stationId + ": Bicis disponibles: " + bikesAvailable
                        + " (Mecánicas: " + mechanicalBikes + ", Eléctricas: " + ebikes
                        + "), Docks disponibles: " + docksAvailable + ", Estado: " + status);
            }
        } catch (Exception e) {
            // Manejar excepción
            e.printStackTrace();
        }
    }

}
