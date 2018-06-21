package com.nuzharukiya.spapp.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nuzharukiya.spapp.room.entities.ServiceInfoEntity;

import java.util.List;

import static com.nuzharukiya.spapp.room.Collection.TABLE_SERVICE_PROVIDER_INFO;

/**
 * Created by Nuzha Rukiya on 18/06/12.
 */

@Dao
public interface ServiceInfoDao {

    @Query("SELECT * FROM " + TABLE_SERVICE_PROVIDER_INFO)
    ServiceInfoEntity getServiceProviderInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServiceProviderInfo(ServiceInfoEntity serviceInfoEntity);

    @Query("SELECT * FROM " + TABLE_SERVICE_PROVIDER_INFO)
    LiveData<List<ServiceInfoEntity>> fetchServiceProviderInfo();
}
