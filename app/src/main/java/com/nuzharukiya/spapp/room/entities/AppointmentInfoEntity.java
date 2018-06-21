package com.nuzharukiya.spapp.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.nuzharukiya.spapp.room.typeConverters.StringListTypeConverter;

import java.util.List;

import static com.nuzharukiya.spapp.room.Collection.APPOINTMENT_DATE;
import static com.nuzharukiya.spapp.room.Collection.APPOINTMENT_FOR;
import static com.nuzharukiya.spapp.room.Collection.APPOINTMENT_ID;
import static com.nuzharukiya.spapp.room.Collection.APPOINTMENT_TIME;
import static com.nuzharukiya.spapp.room.Collection.SERVICE_TYPE;
import static com.nuzharukiya.spapp.room.Collection.TABLE_APPOINTMENT_INFO;

/**
 * Created by Nuzha Rukiya on 18/06/20.
 */

@Entity(tableName = TABLE_APPOINTMENT_INFO)
public class AppointmentInfoEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = APPOINTMENT_ID)
    String appointmentId;

    @ColumnInfo(name = APPOINTMENT_FOR)
    String appointmentFor;

    @ColumnInfo(name = APPOINTMENT_DATE)
    String appointmentDate;

    @ColumnInfo(name = APPOINTMENT_TIME)
    String appointmentTime;

    @ColumnInfo(name = SERVICE_TYPE)
    @TypeConverters(StringListTypeConverter.class)
    List<String> serviceType;

    public AppointmentInfoEntity(@NonNull String appointmentId, String appointmentFor, String appointmentDate, String appointmentTime, List<String> serviceType) {
        this.appointmentId = appointmentId;
        this.appointmentFor = appointmentFor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.serviceType = serviceType;
    }

    @NonNull
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(@NonNull String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentFor() {
        return appointmentFor;
    }

    public void setAppointmentFor(String appointmentFor) {
        this.appointmentFor = appointmentFor;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public List<String> getServiceType() {
        return serviceType;
    }

    public void setServiceType(List<String> serviceType) {
        this.serviceType = serviceType;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
