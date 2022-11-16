package com.example.weathertst.repositroty

import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.database.WeatherDatabase


class WeatherRepository(private val db: WeatherDatabase) {
    suspend fun upsert(location: LocationResponseItem) =
        db.getWeatherDao().upsert(location)

    suspend fun deleteLocation(location: LocationResponseItem) =
        db.getWeatherDao().deleteLocation(location)

    fun getLocationsLive() =
        db.getWeatherDao().getLocationsLive()

    fun getLocations() =
        db.getWeatherDao().getLocations()

    suspend fun checkByLatLon(lat: Double, lon: Double) =
        db.getWeatherDao().checkByLatLon(lat, lon)


}