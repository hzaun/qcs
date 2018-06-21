package com.nuzharukiya.spapp.room.typeConverters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nuzharukiya.spapp.room.entities.ServicesOfferedEntity;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nuzha Rukiya on 18/06/18.
 */
public class StringListTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToStringList(String data) {

        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String StringListToString(List<String> someObjects) {
        return gson.toJson(someObjects);
    }
}
