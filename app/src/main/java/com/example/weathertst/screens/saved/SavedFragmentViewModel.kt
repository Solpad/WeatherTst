package com.example.weathertst.screens.saved

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.model.geocoding.LocationResponse
import com.example.weathertst.repositroty.WeatherMvvmRepo
import com.example.weathertst.utils.Resource

class SavedFragmentViewModel:ViewModel() {

    private val repositoryWeather = WeatherMvvmRepo()
    var selectedLocation: MutableLiveData<LocationResponseItem>? = null
    var location: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()

    fun printRepo(){
        location = repositoryWeather.getLocation()
        Log.e("location saved",location.value?.data.toString())
    }
}