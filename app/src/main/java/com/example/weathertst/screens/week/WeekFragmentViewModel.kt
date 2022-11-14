package com.example.weathertst.screens.week

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertst.api.RetrofitRepository
import com.example.weathertst.model.weekWeather.WeekWeatherResponse
import com.example.weathertst.repositroty.WeatherMvvmRepo
import com.example.weathertst.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class WeekFragmentViewModel :ViewModel(){

    val weekWeather: MutableLiveData<Resource<WeekWeatherResponse>> = MutableLiveData()
    var weekWeatherResponse: WeekWeatherResponse? = null
    var cityLiveData: MutableLiveData<String> = MutableLiveData()
    private val repositoryWeather = WeatherMvvmRepo()

    fun getWeekWeather() = viewModelScope.launch {
        weekWeather.postValue(Resource.Loading())
        try {
            val response = RetrofitRepository().retrofitService.getWeekWeather(cityLiveData.value!!)
            weekWeather.postValue(handleWeekWeatherResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> weekWeather.postValue(Resource.Error("Network Failure"))
                else -> weekWeather.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleWeekWeatherResponse(response: Response<WeekWeatherResponse>): Resource<WeekWeatherResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                weekWeatherResponse = resultResponse
                return Resource.Success(weekWeatherResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}