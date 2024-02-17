package com.example.dam2_m08_uf2_t1.main;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPref {

    private static final String PREF_NAME = "BicingPrefs";
    private static final String KEY_FAVORITE_LOCATIONS = "favorite";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    public static void addFavoriteLocation(Context context, int locationId) {
        Set<String> favoriteLocations = getFavoriteLocations(context);
        favoriteLocations.add(String.valueOf(locationId));
        saveFavoriteLocations(context, favoriteLocations);
    }

    public static void removeFavoriteLocation(Context context, int locationId) {
        Set<String> favoriteLocations = getFavoriteLocations(context);
        favoriteLocations.remove(String.valueOf(locationId));
        saveFavoriteLocations(context, favoriteLocations);
    }

    public static Set<String> getFavoriteLocations(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(KEY_FAVORITE_LOCATIONS, new HashSet<>());
    }

    private static void saveFavoriteLocations(Context context, Set<String> favoriteLocations) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(KEY_FAVORITE_LOCATIONS, favoriteLocations);
        editor.apply();
    }

}
