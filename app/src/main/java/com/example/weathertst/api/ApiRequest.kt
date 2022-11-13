package com.example.weathertst.api

import com.example.weathertst.model.currentWeather.CurrentWeatherResponse
import com.example.weathertst.utils.AppId

import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET

import retrofit2.http.Query

interface ApiRequest {

    //  https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=c0c4a4b4047b97ebc5948ac9c48c0559&units=metric
    //  https://api.openweathermap.org/data/2.5/weather?q=Moscow&lang=ru&appid=c0c4a4b4047b97ebc5948ac9c48c0559&units=metric

    @GET("data/2.5/weather?")
    fun getCurrentWeatherDataCity(
        @Query("q")
        city: String,
        @Query("lang")
        lang: String = com.example.weathertst.utils.lang,
        @Query("appid")
        app_id: String = AppId,
        @Query("units")
        units: String = com.example.weathertst.utils.units
    ): Call<CurrentWeatherResponse>

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

/*
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("appid")
        apiKey: String = AppId,
        @Query("units")
        units: String = units,
        @Query("lang")
        language: String = lang
    ): Response<CurrentWeatherResponse>

    @GET("geo/1.0/direct")
    suspend fun getLocationUsingCity(
        @Query("q")
        city: String?,
        @Query("appid")
        apiKey: String? = API_KEY,
        @Query("limit")
        limit: Int = 5
    ): Response<LocationResponse>

    @GET("data/2.5/forecast/hourly")
    suspend fun getHourlyWeather(
        @Query("lat")
        lat: Double,
        @Query("lon")
        lon: Double,
        @Query("appid")
        apiKey: String = API_KEY,
        @Query("lang")
        language: String = LANGUAGE
    ): Response<HourlyWeatherResponse>
*/

}