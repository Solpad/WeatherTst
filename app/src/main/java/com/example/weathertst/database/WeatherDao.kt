package com.example.weathertst.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.models.geocoding.LocationResponseItem

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(location: LocationResponseItem): Long

    @Delete
    suspend fun deleteLocation(location: LocationResponseItem)
}