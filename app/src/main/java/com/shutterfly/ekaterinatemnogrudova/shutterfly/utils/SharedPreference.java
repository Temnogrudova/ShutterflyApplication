package com.shutterfly.ekaterinatemnogrudova.shutterfly.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import com.shutterfly.ekaterinatemnogrudova.shutterfly.models.Image;

public class SharedPreference {

    public static final String PREFS_NAME = "IMAGES_APP";
    public static final String FAVORITES = "Image_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Image> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Image image) {
        List<Image> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Image>();
        favorites.add(image);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Image image) {
        ArrayList<Image> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(image);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Image> getFavorites(Context context) {
        SharedPreferences settings;
        List<Image> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Image[] favoriteItems = gson.fromJson(jsonFavorites,
                    Image[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Image>(favorites);
        } else
            return null;

        return (ArrayList<Image>) favorites;
    }
}
