package com.example.weathertst.screens.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathertst.repositroty.WeatherRepository

class WeatherViewModelProviderFactory(
    val app: Application,
    val weatherRepository: WeatherRepository
    ): ViewModelProvider.Factory {

}