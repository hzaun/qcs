package com.nuzharukiya.spapp.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static com.nuzharukiya.spapp.room.Collection.SERVICE_AVAILABLE;
import static com.nuzharukiya.spapp.room.Collection.SERVICE_ID;
import static com.nuzharukiya.spapp.room.Collection.SERVICE_NAME;
import static com.nuzharukiya.spapp.room.Collection.SERVICE_PRICE;
import static com.nuzharukiya.spapp.room.Collection.TABLE_SERVICES_OFFERED;

/**
 * Created by Nuzha Rukiya on 18/06/15.
 */
@Entity(tableName = TABLE_SERVICES_OFFERED)
public class ServicesOfferedEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = SERVICE_ID)
    String serviceId;

    @ColumnInfo(name = SERVICE_NAME)
    String serviceName;

    @ColumnInfo(name = SERVICE_PRICE)
    String servicePrice;

    @ColumnInfo(name = SERVICE_AVAILABLE)
    boolean bServiceAvailable;

    public ServicesOfferedEntity(String serviceName, String servicePrice) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.bServiceAvailable = true;
    }

    @NonNull
    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public boolean isbServiceAvailable() {
        return bServiceAvailable;
    }

    public void setbServiceAvailable(boolean bServiceAvailable) {
        this.bServiceAvailable = bServiceAvailable;
    }
}
