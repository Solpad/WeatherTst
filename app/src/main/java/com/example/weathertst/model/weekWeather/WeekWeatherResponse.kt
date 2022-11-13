package com.example.weathertst.model.weekWeather

data class WeekWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<InfoWeek>,
    val message: Int
)