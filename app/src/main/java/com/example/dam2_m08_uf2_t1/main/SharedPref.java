package com.example.dam2_m08_uf2_t1.main;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class SharedPref {

    // Nombre del archivo de preferencias compartidas
    private static final String PREF_NAME = "BicingPrefs";
    // Clave para las ubicaciones favoritas en las preferencias compartidas
    private static final String KEY_FAVORITE_LOCATIONS = "favorite";

    // Obtiene el objeto SharedPreferences
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    // Agrega una ubicación favorita al conjunto de ubicaciones favoritas.
    public static void addFavoriteLocation(Context context, int locationId) {
        Log.d("SharedPref", "Adding location to favorites: " + locationId);
        Set<String> favoriteLocations = getFavoriteLocationsSet(context);
        favoriteLocations.add(String.valueOf(locationId));
        saveFavoriteLocations(context, favoriteLocations);
    }
    // Elimina una ubicación favorita del conjunto de ubicaciones favoritas.
    public static void removeFavoriteLocation(Context context, int locationId) {
        Log.d("SharedPref", "Removing location from favorites: " + locationId);
        Set<String> favoriteLocations = getFavoriteLocationsSet(context);
        favoriteLocations.remove(String.valueOf(locationId));
        saveFavoriteLocations(context, favoriteLocations);
    }

    // Obtiene el conjunto de ubicaciones favoritas como un conjunto de enteros.
    public static Set<Integer> getFavoriteLocationsInt(Context context) {
        Log.d("SharedPref", "Getting favorite locations");
        Set<String> stringSet = getFavoriteLocationsSet(context);
        Set<Integer> integerSet = new HashSet<>();

        for (String value : stringSet) {
            integerSet.add(Integer.parseInt(value));
        }

        return integerSet;
    }
    // Obtiene el conjunto de ubicaciones favoritas como un conjunto de cadenas.
    private static Set<String> getFavoriteLocationsSet(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(KEY_FAVORITE_LOCATIONS, new HashSet<String>());
    }
    // Guarda el conjunto de ubicaciones favoritas en las preferencias compartidas.
    private static void saveFavoriteLocations(Context context, Set<String> favoriteLocations) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(KEY_FAVORITE_LOCATIONS, favoriteLocations);
        editor.apply();
    }
}
