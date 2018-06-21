package com.nuzharukiya.spapp.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.nuzharukiya.spapp.room.typeConverters.ServicesOfferedListTypeConverter;

import java.util.HashMap;
import java.util.List;

import static com.nuzharukiya.spapp.room.Collection.FRIDAY;
import static com.nuzharukiya.spapp.room.Collection.FULL_NAME;
import static com.nuzharukiya.spapp.room.Collection.MONDAY;
import static com.nuzharukiya.spapp.room.Collection.PHONE_NO;
import static com.nuzharukiya.spapp.room.Collection.SATURDAY;
import static com.nuzharukiya.spapp.room.Collection.SERVICES_OFFERED;
import static com.nuzharukiya.spapp.room.Collection.SUNDAY;
import static com.nuzharukiya.spapp.room.Collection.TABLE_SERVICE_PROVIDER_INFO;
import static com.nuzharukiya.spapp.room.Collection.THURSDAY;
import static com.nuzharukiya.spapp.room.Collection.TUESDAY;
import static com.nuzharukiya.spapp.room.Collection.WEDNESDAY;
import static com.nuzharukiya.spapp.room.Collection.WORKING_DAYS;

/**
 * Created by Nuzha Rukiya on 18/06/12.
 */

@Entity(tableName = TABLE_SERVICE_PROVIDER_INFO)
public class ServiceInfoEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = PHONE_NO)
    private String phoneNo;

    @ColumnInfo(name = FULL_NAME)
    private String fullName;

    @ColumnInfo(name = WORKING_DAYS)
    private HashMap<String, Boolean> workingDaysMap = new HashMap<>();

    @ColumnInfo(name = SERVICES_OFFERED)
    @TypeConverters(ServicesOfferedListTypeConverter.class)
    private List<ServicesOfferedEntity> servicesOfferedList;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setWorkingDaysMap(HashMap<String, Boolean> workingDaysMap) {
        this.workingDaysMap = workingDaysMap;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getFullName() {
        return fullName;
    }

    public HashMap<String, Boolean> getWorkingDaysMap() {
        return workingDaysMap;
    }

    public List<ServicesOfferedEntity> getServicesOfferedList() {
        return servicesOfferedList;
    }

    public void setServicesOfferedList(List<ServicesOfferedEntity> servicesOfferedList) {
        this.servicesOfferedList = servicesOfferedList;
    }

    public void setWorkingDays(HashMap<String, Boolean> workingDaysMap) {
        workingDaysMap.clear();
        workingDaysMap.putAll(workingDaysMap);
    }
}
