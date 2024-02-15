package com.example.dam2_m08_uf2_t1.apis;

import com.example.dam2_m08_uf2_t1.modelo.EstacionEstat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClientEstatEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/";
    private static final String DATASET_ID = "6aa3416d-ce1a-494d-861b-7bd07f069600";  // Reemplaza con el identificador real del conjunto de datos
    private static final String RESOURCE_ID = "1b215493-9e63-4a12-8980-2d7e0fa19f85";  // Reemplaza con el identificador real del recurso
    private static final String TOKEN = "4426b8c0c77727dfe8f453247a9ede96286a9dfdfb6cbbc98c43d1ed409a5dde";

    public static ArrayList<EstacionEstat> obtenerDatosEstatEstacions() {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + DATASET_ID + "/resource/" + RESOURCE_ID + "/download/recurs.json";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", TOKEN)
                .build();

        try {
            System.out.println("--------------------------------------------");
            System.out.println(request);
            Response response = client.newCall(request).execute();
            System.out.println(response);
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                return parseJsonData(responseData);
            } else {
                // Manejar error
                return null;
            }
        } catch (Exception e) {
            // Manejar excepción
            e.printStackTrace();
            return null;

        }
    }

    private static ArrayList<EstacionEstat> parseJsonData(String jsonData) {
        ArrayList<EstacionEstat> estacionesEstatList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonEstacion = jsonArray.getJSONObject(i);

                String address = jsonEstacion.getString("address");
                String status = jsonEstacion.getString("status");
                int bikesAvailable = jsonEstacion.getInt("bikesAvailable");

                EstacionEstat estacion = new EstacionEstat(address, status, bikesAvailable);
                estacionesEstatList.add(estacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estacionesEstatList;
    }
}
