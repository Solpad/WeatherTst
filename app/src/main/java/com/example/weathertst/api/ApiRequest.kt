package com.example.weathertst.api

import com.example.weathertst.model.WeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {

    //  https://api.openweathermap.org/data/2.5/weather?q=Moscow&appid=c0c4a4b4047b97ebc5948ac9c48c0559&units=metric
    //  https://api.openweathermap.org/data/2.5/weather?q=Moscow&lang=ru&appid=c0c4a4b4047b97ebc5948ac9c48c0559&units=metric

    @GET("data/2.5/weather?")
        fun getCurrentWeatherDataCity(@Query("q") city: String, @Query("lang") lang: String, @Query("appid") app_id: String, @Query("units") units: String): Call<WeatherResponse>
}