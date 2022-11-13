package com.example.weathertst.screens.main


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weathertst.api.RetrofitRepository
import com.example.weathertst.model.currentWeather.CurrentWeatherResponse
import com.example.weathertst.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.io.IOException


class MainFragmentViewModel(app:Application): AndroidViewModel(app) {

    val currentWeather: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    var currentWeatherResponse: CurrentWeatherResponse? = null
    var cityLiveData: MutableLiveData<String> = MutableLiveData()

    fun getCurrentWeather() = viewModelScope.launch {
        currentWeather.postValue(Resource.Loading())
        try {
            val response = RetrofitRepository().retrofitService.getCurrentWeather(cityLiveData.value!!)
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
}
