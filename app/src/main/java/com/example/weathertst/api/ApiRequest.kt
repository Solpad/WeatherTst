package com.example.weathertst.api

import com.example.weathertst.model.currentWeather.CurrentWeatherResponse
import com.example.weathertst.model.weekWeather.WeekWeatherResponse
import com.example.weathertst.utils.AppId

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
}