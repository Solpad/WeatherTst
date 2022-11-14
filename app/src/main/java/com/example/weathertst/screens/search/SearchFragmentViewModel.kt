package com.example.weathertst.screens.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.api.RetrofitRepository
import com.example.weathertst.database.WeatherDatabase
import com.example.weathertst.model.geocoding.LocationResponse
import com.example.weathertst.repositroty.WeatherRepository
import com.example.weathertst.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class SearchFragmentViewModel (
    app: Application
): AndroidViewModel(app){

    private val weatherRepository = WeatherRepository(WeatherDatabase(app))

    var location: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()

    var locationResponse: LocationResponse? = null
    var selectedLocation: MutableLiveData<LocationResponseItem>? = null


    fun getLocationUsingCity(city: String) = viewModelScope.launch {
        try {
            Log.e("getLocation city= ",city)
            val response = RetrofitRepository().retrofitService.getLocationUsingCity(city)
            Log.e("getLocation RESPONSE= ",response.body().toString())
            location.postValue(handleLocationUsingCityResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> location.postValue(Resource.Error("Network Failure"))
                else -> location.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private suspend fun handleLocationUsingCityResponse(response: Response<LocationResponse>): Resource<LocationResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                resultResponse.forEach { location ->
                    location.id = location.lat.hashCode()
                }
                locationResponse = resultResponse
                return Resource.Success(locationResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveLocation(location: LocationResponseItem) = viewModelScope.launch {
        weatherRepository.upsert(location)
    }

    fun deleteLocation(location: LocationResponseItem) = viewModelScope.launch {
        weatherRepository.deleteLocation(location)
    }

}