package com.example.weathertst.screens.main


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.api.RetrofitRepository
import com.example.weathertst.model.currentWeather.CurrentWeatherResponse
import com.example.weathertst.model.geocoding.LocationResponse
import com.example.weathertst.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class MainFragmentViewModel(app: Application) : AndroidViewModel(app) {

    val currentWeather: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    var currentWeatherResponse: CurrentWeatherResponse? = null
    var cityLiveData: MutableLiveData<String> = MutableLiveData()

    var location: MutableLiveData<Resource<LocationResponse>> = MutableLiveData()

    var locationResponse: LocationResponse? = null
    var selectedLocation: MutableLiveData<LocationResponseItem>? = null
    val currentWeatherLat: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    var currentWeatherResponseLat: CurrentWeatherResponse? = null

    fun getCurrentWeather() = viewModelScope.launch {
        currentWeather.postValue(Resource.Loading())
        try {
            val response =
                RetrofitRepository().retrofitService.getCurrentWeather(cityLiveData.value!!)
            currentWeather.postValue(handleCurrentWeatherResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> currentWeather.postValue(Resource.Error("Network Failure"))
                else -> currentWeather.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleCurrentWeatherResponse(response: Response<CurrentWeatherResponse>): Resource<CurrentWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                currentWeatherResponse = resultResponse
                return Resource.Success(currentWeatherResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getLocationUsingCity(city: String) = viewModelScope.launch {
        try {
            Log.e("City",city)
            val response = RetrofitRepository().retrofitService.getLocationUsingCity(city)
            Log.e("RESPONSE",response.body().toString())
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

    fun getCurrentWeatherLat(lat: Double, lon: Double) = viewModelScope.launch {

        currentWeatherLat.postValue(Resource.Loading())
        try {
            val response = RetrofitRepository().retrofitService.getCurrentWeather(lat, lon)
            currentWeatherLat.postValue(handleCurrentWeatherResponseLat(response))

        } catch (t: Throwable) {
            when (t) {
                is IOException -> currentWeatherLat.postValue(Resource.Error("Network Failure"))
                else -> currentWeatherLat.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleCurrentWeatherResponseLat(response: Response<CurrentWeatherResponse>): Resource<CurrentWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                currentWeatherResponseLat = resultResponse
                return Resource.Success(currentWeatherResponseLat ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}
