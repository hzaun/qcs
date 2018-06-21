package com.nuzharukiya.spapp.room;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Nuzha Rukiya on 18/06/14.
 */
public class Converters {

    @TypeConverter
    public static HashMap<String, Boolean> fromString(String value) {
        Type listType = new TypeToken<HashMap<String, Boolean>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromHashMap(HashMap<String, Boolean> hashMap) {
        Gson gson = new Gson();
        String json = gson.toJson(hashMap);
        return json;
    }
}
