package com.example.dam2_m08_uf2_t1.apis;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClientEstatEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/";
    private static final String DATASET_ID = "estat-estacions-bicing";  // Reemplaza con el identificador real del conjunto de datos
    private static final String RESOURCE_ID = "b20e711d-c3bf-4fe5-9cde-4de94c5f588f";  // Reemplaza con el identificador real del recurso
    private static final String TOKEN = "9eff5a7842e152d2a0d8d76728bae2db134fd8796c9fc5a466fe8ffe0e72e2d5";

    public static String obtenerDatosEstatEstacions() {
        OkHttpClient client = new OkHttpClient();

        String url = BASE_URL + DATASET_ID + "/resource/" + RESOURCE_ID + "/download/recurs.json";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", TOKEN)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
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
}
