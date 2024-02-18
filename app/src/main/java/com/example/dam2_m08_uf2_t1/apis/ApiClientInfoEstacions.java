package com.example.dam2_m08_uf2_t1.apis;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dam2_m08_uf2_t1.modelo.Estacion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiClientInfoEstacions {

    private static final String BASE_URL = "https://opendata-ajuntament.barcelona.cat/data/dataset/bd2462df-6e1e-4e37-8205-a4b8e7313b84/resource/f60e9291-5aaa-417d-9b91-612a9de800aa/download";

    private static final String TOKEN = "4426b8c0c77727dfe8f453247a9ede96286a9dfdfb6cbbc98c43d1ed409a5dde";
    private ArrayList<Estacion> estacionInfo = new ArrayList<Estacion>();
    public interface OnDataFetchedListener {
        void onSuccess(ArrayList<Estacion> estacionEStat);

        void onError(VolleyError error);
    }

    private static RequestQueueSingleton requestQueueSingleton;

    public static void obtenerDatosinfoEstacions(Context context,ArrayList<Estacion> estacionesEstat, OnDataFetchedListener listener) {
        if (requestQueueSingleton == null) {
            requestQueueSingleton = RequestQueueSingleton.getInstance(context);
        }

        RequestQueue requestQueue = requestQueueSingleton.getRequestQueue();

        String url = BASE_URL;//enlace del JSON

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        if (listener != null) {
                            ArrayList<Estacion> estacionInfo = procesarInformacion(response.toString(),estacionesEstat);
                            listener.onSuccess(estacionInfo);
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

    private static ArrayList<Estacion> procesarInformacion(String responseData,ArrayList<Estacion> estacionesEstat) {
        ArrayList<Estacion> estacionInfo = new ArrayList<Estacion>();
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray stations = data.getJSONArray("stations");

            for (int i = 0; i < stations.length(); i++) {

                JSONObject station = stations.getJSONObject(i);
                int stationId = station.getInt("station_id");
                String name = station.getString("name");
                String physical_configuration = station.getString("physical_configuration");
                double lat = station.getDouble("lat");
                double lon = station.getDouble("lon");
                double altitude = station.getDouble("altitude");
                String address = station.getString("address");
                String post_code = station.getString("post_code");
                int capacity = station.getInt("capacity");
                //boolean is_charging_station = station.getBoolean("is_charging_station");//creo q no hace falta pq ya esta en el estat
                double nearby_distance = station.getDouble("nearby_distance");
                boolean _ride_code_support = station.getBoolean("_ride_code_support");

                int rental_uris = 0; // Valor predeterminado


                for (Estacion e : estacionesEstat){
                    if (e.getStationId() == stationId ){
                        e.setName(name);
                        e.setPhysicalConfiguration(physical_configuration);
                        e.setLat(lat);
                        e.setLon(lon);
                        e.setAltitude(altitude);
                        e.setAddress(address);
                        e.setPostCode(post_code);
                        e.setCapacity(capacity);
                        //e.setIs_charging_station(is_charging_station);//creo q no hace falta pq ya esta en el estat
                        e.setNearbyDistance(nearby_distance);
                        e.setRideCodeSupport(_ride_code_support);
                        e.setRentalUris(rental_uris);

                      // System.out.println(e);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
        return estacionesEstat;
    }
}
