package com.example.weathertst.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathertst.api.RetrofitRepository
import com.example.weathertst.model.WeatherResponse
import com.example.weathertst.utils.AppId
import com.example.weathertst.utils.lang
import com.example.weathertst.utils.units
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class MainFragmentViewModel: ViewModel() {

    val myResponse: MutableLiveData<WeatherResponse> by lazy{
        MutableLiveData<WeatherResponse>()
    }
    var cityLiveData: MutableLiveData<String> = MutableLiveData()




    fun getCurrentData():LiveData<WeatherResponse> {


        val callMain = RetrofitRepository().retrofitService.getCurrentWeatherDataCity(cityLiveData.value!!,lang ,AppId ,units )

        callMain.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {

                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    myResponse.value = weatherResponse

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                println("Error: " + t.message)

            }
        })
    return myResponse
    }



}
