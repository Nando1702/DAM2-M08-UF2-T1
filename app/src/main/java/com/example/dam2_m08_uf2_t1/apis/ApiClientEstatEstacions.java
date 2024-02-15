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
    private static final String DATASET_ID = "estat-estacions-bicing";  // Reemplaza con el identificador real del conjunto de datos
    private static final String RESOURCE_ID = "b20e711d-c3bf-4fe5-9cde-4de94c5f588f";  // Reemplaza con el identificador real del recurso
    private static final String TOKEN = "9eff5a7842e152d2a0d8d76728bae2db134fd8796c9fc5a466fe8ffe0e72e2d5";

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
