package com.example.weathertst.repositroty

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.model.geocoding.LocationResponse
import com.example.weathertst.utils.Resource

class WeatherMvvmRepo {

    fun getLocation():MutableLiveData<Resource<LocationResponse>>{
        return locations
    }
    fun setLocation(location: MutableLiveData<Resource<LocationResponse>>){
        locations = location
    }

    fun getSelectedLocation():MutableLiveData<LocationResponseItem>?{
        return selectedLocation
    }
    fun setSelectedLocation(selectlocation: MutableLiveData<LocationResponseItem>?){
        selectedLocation = selectlocation
    }

    companion object{
        var locations: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()
        var locationResponse: LocationResponse? = null
        var selectedLocation: MutableLiveData<LocationResponseItem>? = null

    }
}