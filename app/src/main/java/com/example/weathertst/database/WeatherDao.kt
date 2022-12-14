package com.example.weathertst.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherapp.models.geocoding.LocationResponseItem

@Dao
interface WeatherDao {
    @Query("SELECT * FROM locations")
    fun getLocationsLive(): LiveData<List<LocationResponseItem>>

    @Query("SELECT * FROM locations")
    fun getLocations(): List<LocationResponseItem>

    @Query("SELECT EXISTS (SELECT 1 FROM locations WHERE lat = :lat AND lon = :lon)")
    suspend fun checkByLatLon(lat: Double, lon: Double): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(location: LocationResponseItem): Long

    @Delete
    suspend fun deleteLocation(location: LocationResponseItem)
}