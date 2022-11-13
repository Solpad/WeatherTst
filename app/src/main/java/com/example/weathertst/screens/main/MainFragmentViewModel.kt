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

    val currentWeatherCity: MutableLiveData<CurrentWeatherResponse> by lazy{ MutableLiveData<CurrentWeatherResponse>() }


    val currentWeather: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    var currentWeatherResponse: CurrentWeatherResponse? = null
    var cityLiveData: MutableLiveData<String> = MutableLiveData()
    fun getCurrentData():LiveData<CurrentWeatherResponse> {


        val callMain = RetrofitRepository().retrofitService.getCurrentWeatherDataCity(cityLiveData.value!!)

        callMain.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {

                if (response.code() == 200) {
                    val currentWeatherCityResponse = response.body()!!

                    currentWeatherCity.postValue(currentWeatherCityResponse)

                }
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {

            }
        })
    return currentWeatherCity
    }

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

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<WeatherApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?. run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}
