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
public class ServicesOfferedListTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<ServicesOfferedEntity> stringToServicesOfferedList(String data) {

        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ServicesOfferedEntity>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String servicesOfferedListToString(List<ServicesOfferedEntity> someObjects) {
        return gson.toJson(someObjects);
    }
}
