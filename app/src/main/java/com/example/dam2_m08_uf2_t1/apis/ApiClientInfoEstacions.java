package com.example.dam2_m08_uf2_t1.apis;

import com.example.dam2_m08_uf2_t1.modelo.EstacionInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClientInfoEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/";
    private static final String DATASET_ID = "bd2462df-6e1e-4e37-8205-a4b8e7313b84";  // Reemplaza con el identificador real del conjunto de datos
    private static final String RESOURCE_ID = "f60e9291-5aaa-417d-9b91-612a9de800aa";  // Reemplaza con el identificador real del recurso
    private static final String TOKEN = "4426b8c0c77727dfe8f453247a9ede96286a9dfdfb6cbbc98c43d1ed409a5dde";

    public static List<EstacionInfo> obtenerDatosInfoEstacions() {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + DATASET_ID + "/resource/" + RESOURCE_ID + "/download/recurs.json";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", TOKEN)
                .build();

        try {
            Response response = client.newCall(request).execute();
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

    private static List<EstacionInfo> parseJsonData(String jsonData) {
        List<EstacionInfo> estacionesInfoList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonEstacion = jsonArray.getJSONObject(i);

                String address = jsonEstacion.getString("address");
                String status = jsonEstacion.getString("status");
                int bikesAvailable = jsonEstacion.getInt("bikesAvailable");

                EstacionInfo estacion = new EstacionInfo(address, status, bikesAvailable);
                estacionesInfoList.add(estacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estacionesInfoList;
    }
}