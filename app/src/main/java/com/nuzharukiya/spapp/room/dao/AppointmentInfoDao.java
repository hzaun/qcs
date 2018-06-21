package com.nuzharukiya.spapp.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nuzharukiya.spapp.room.entities.AppointmentInfoEntity;

import java.util.List;

import static com.nuzharukiya.spapp.room.Collection.TABLE_APPOINTMENT_INFO;

/**
 * Created by Nuzha Rukiya on 18/06/20.
 */

@Dao
public interface AppointmentInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAppointmentData(List<AppointmentInfoEntity> appointmentInfoEntityList);

    @Query("SELECT * FROM " + TABLE_APPOINTMENT_INFO)
    LiveData<List<AppointmentInfoEntity>> getAllAppointmentInfo();
}
