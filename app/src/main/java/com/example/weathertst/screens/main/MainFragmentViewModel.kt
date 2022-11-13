package com.example.weathertst.screens.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathertst.api.RetrofitRepository
import com.example.weathertst.model.currentWeather.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class MainFragmentViewModel(): ViewModel() {

    val myResponse: MutableLiveData<CurrentWeatherResponse> by lazy{ MutableLiveData<CurrentWeatherResponse>() }
    var cityLiveData: MutableLiveData<String> = MutableLiveData()
/*
    val currentWeather: MutableLiveData<Resource<CurrentWeatherResponse>> = MutableLiveData()
    var currentWeatherResponse: CurrentWeatherResponse? = null
*/


    fun getCurrentData():LiveData<CurrentWeatherResponse> {


        val callMain = RetrofitRepository().retrofitService.getCurrentWeatherDataCity(cityLiveData.value!!)

        callMain.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {

                if (response.code() == 200) {
                    val CurrentWeatherResponse = response.body()!!

                    myResponse.value = CurrentWeatherResponse

                }
            }

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                println("Error: " + t.message)

            }
        })
    return myResponse
    }



}
