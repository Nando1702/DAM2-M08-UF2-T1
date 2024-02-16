package com.example.dam2_m08_uf2_t1.apis;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ApiClientEstatEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/6aa3416d-ce1a-494d-861b-7bd07f069600/resource/1b215493-9e63-4a12-8980-2d7e0fa19f85/download";

    private static final String TOKEN = "4426b8c0c77727dfe8f453247a9ede96286a9dfdfb6cbbc98c43d1ed409a5dde";

    public interface OnDataFetchedListener {
        void onSuccess(JSONObject response);

        void onError(VolleyError error);
    }

    private static RequestQueueSingleton requestQueueSingleton;

    public static void obtenerDatosEstatEstacions(Context context,
                                                  OnDataFetchedListener listener) {
        if (requestQueueSingleton == null) {
            requestQueueSingleton = RequestQueueSingleton.getInstance(context);
        }

        RequestQueue requestQueue = requestQueueSingleton.getRequestQueue();

        String url = BASE_URL;//+ DATASET_ID + "/resource/" + RESOURCE_ID + "/download/recurs.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            listener.onSuccess(response);
                            System.out.println(response);
                            System.out.println("-----------------------------------------------");
                            System.out.println(response.toString());
                            procesarInformacion(response.toString());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listener != null) {
                            listener.onError(error);
                        }
                        Log.e("Volley Error", "Error en la solicitud: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                System.out.println("holiiiiiiiiiiiiiiiiiiiiiii");
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", TOKEN);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
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

                // Handle station information as needed
                Log.d("Estación", "ID: " + stationId + ", Bicis disponibles: " + bikesAvailable
                        + " (Mecánicas: " + mechanicalBikes + ", Eléctricas: " + ebikes
                        + "), Docks disponibles: " + docksAvailable + ", Estado: " + status);
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
    }
}
