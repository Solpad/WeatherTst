package com.example.weathertst.api

import com.example.weathertst.model.currentWeather.CurrentWeatherResponse
import com.example.weathertst.model.geocoding.LocationResponse
import com.example.weathertst.model.weekWeather.WeekWeatherResponse
import com.example.weathertst.model.weeksWeather.WeeksWeatherResponse
import com.example.weathertst.utils.AppId
import com.example.weathertst.utils.lang

import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET

import retrofit2.http.Query

interface ApiRequest {

    //  https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=c0c4a4b4047b97ebc5948ac9c48c0559&units=metric
    //  https://api.openweathermap.org/data/2.5/weather?q=Moscow&lang=ru&appid=c0c4a4b4047b97ebc5948ac9c48c0559&units=metric

    @GET("data/2.5/weather?")
    suspend fun getCurrentWeather(
        @Query("q")
        city: String,
        @Query("lang")
        lang: String = com.example.weathertst.utils.lang,
        @Query("appid")
        app_id: String = AppId,
        @Query("units")
        units: String = com.example.weathertst.utils.units
    ): Response<CurrentWeatherResponse>
    @GET("data/2.5/weather")

    suspend fun getCurrentWeather(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("appid")
        apiKey: String = AppId,
        @Query("units")
        units: String = com.example.weathertst.utils.units,
        @Query("lang")
        language: String = lang
    ): Response<CurrentWeatherResponse>

    @GET("/data/2.5/forecast")
    suspend fun getWeekWeather(
        @Query("q")
        city: String,
        @Query("lang")
        lang: String = com.example.weathertst.utils.lang,
        @Query("appid")
        app_id: String = AppId,
        @Query("units")
        units: String = com.example.weathertst.utils.units
    ):Response<WeekWeatherResponse>

    @GET("geo/1.0/direct")
    suspend fun getLocationUsingCity(
        @Query("q")
        city: String?,
        @Query("appid")
        apiKey: String? = AppId,
        @Query("limit")
        limit: Int = 5
    ): Response<LocationResponse>

    @GET("/data/2.5/forecast/daily")
    suspend fun getWeeksWeather(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("units")
        units: String = com.example.weathertst.utils.units,
        @Query("cnt")
        cnt:Int = 7,
        @Query("lang")
        lang: String = com.example.weathertst.utils.lang,
        @Query("appid")
        app_id: String = AppId,
    ):Response<WeeksWeatherResponse>

}