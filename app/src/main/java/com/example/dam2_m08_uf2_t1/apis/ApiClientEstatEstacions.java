package com.example.dam2_m08_uf2_t1.apis;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiClientEstatEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/6aa3416d-ce1a-494d-861b-7bd07f069600/resource/1b215493-9e63-4a12-8980-2d7e0fa19f85/download";

    private static final String TOKEN = "4426b8c0c77727dfe8f453247a9ede96286a9dfdfb6cbbc98c43d1ed409a5dde";
    private ArrayList<Estacion> estacionEStat = new ArrayList<Estacion>();
    public interface OnDataFetchedListener {
        void onSuccess(ArrayList<Estacion> estacionEStat);

        void onError(VolleyError error);
    }

    private static RequestQueueSingleton requestQueueSingleton;

    public static void obtenerDatosEstatEstacions(Context context,
                                                                      OnDataFetchedListener listener) {
        if (requestQueueSingleton == null) {
            requestQueueSingleton = RequestQueueSingleton.getInstance(context);
        }

        RequestQueue requestQueue = requestQueueSingleton.getRequestQueue();

        String url = BASE_URL;//enlace del JSON

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            ArrayList<Estacion> estacionEStat = procesarInformacion(response.toString());
                            listener.onSuccess(estacionEStat);
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", TOKEN);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private static ArrayList<Estacion> procesarInformacion(String responseData) {
        ArrayList<Estacion> estacionEStat = new ArrayList<Estacion>();
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray stations = data.getJSONArray("stations");

            for (int i = 0; i < stations.length(); i++) {

                JSONObject station = stations.getJSONObject(i);
                int stationId = station.getInt("station_id");
                int num_bikes_available = station.getInt("num_bikes_available");
                int bikes_mechanical = station.getJSONObject("num_bikes_available_types").getInt("mechanical");
                int bikes_ebike = station.getJSONObject("num_bikes_available_types").getInt("ebike");
                int num_docks_available = station.getInt("num_docks_available");
                int last_reported = station.getInt("last_reported");
                boolean is_charging_station = station.getBoolean("is_charging_station");
                String status = station.getString("status");
                int is_installed = station.getInt("is_installed");
                int is_renting = station.getInt("is_renting");
                int is_returning = station.getInt("is_returning");

                int traffic = 0;

                Estacion aux = new Estacion(stationId, num_bikes_available,
                        bikes_mechanical, bikes_ebike,
                        num_docks_available, last_reported,
                        is_charging_station, status, is_installed,
                        is_renting, is_returning, traffic);
                // System.out.println(aux);

                estacionEStat.add(aux);
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
        return estacionEStat;
    }
}
