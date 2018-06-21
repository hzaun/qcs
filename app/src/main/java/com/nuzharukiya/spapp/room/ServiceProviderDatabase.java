package com.nuzharukiya.spapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.nuzharukiya.spapp.room.dao.AppointmentInfoDao;
import com.nuzharukiya.spapp.room.dao.ServiceInfoDao;
import com.nuzharukiya.spapp.room.entities.AppointmentInfoEntity;
import com.nuzharukiya.spapp.room.entities.ServiceInfoEntity;

import static com.nuzharukiya.spapp.room.Collection.DATABASE_SERVICE_PROVIDER;

/**
 * Created by Nuzha Rukiya on 18/06/12.
 */
@Database(entities = {ServiceInfoEntity.class, AppointmentInfoEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ServiceProviderDatabase extends RoomDatabase {

    private static ServiceProviderDatabase INSTANCE;

    public abstract ServiceInfoDao serviceInfoDao();

    public abstract AppointmentInfoDao appointmentInfoDao();

    public static ServiceProviderDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(
                            context.getApplicationContext(),
                            ServiceProviderDatabase.class,
                            DATABASE_SERVICE_PROVIDER)
                            .build();
        }
        return INSTANCE;
    }

    public static ServiceProviderDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ServiceProviderDatabase.class, DATABASE_SERVICE_PROVIDER).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}