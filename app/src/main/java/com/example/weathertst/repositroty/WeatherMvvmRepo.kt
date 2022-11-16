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

    fun getLon():Double?{
        return lon
    }
    fun setLon(lonWeek: Double){
        lon = lonWeek
    }
    fun getLat():Double?{
        return lat
    }
    fun setLat(latWeek: Double){
        lat = latWeek
    }

    fun getLists():List<LocationResponseItem>?{
        return lists
    }
    fun setLists(listsThis: List<LocationResponseItem>){
        lists = listsThis
    }
    companion object{
        var locations: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()
        var selectedLocation: MutableLiveData<LocationResponseItem>? = null
        var lat: Double? = null
        var lon: Double? = null
        var lists: List<LocationResponseItem>? = null

    }
}