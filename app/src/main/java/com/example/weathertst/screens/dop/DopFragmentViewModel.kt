package com.example.weathertst.screens.dop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DopFragmentViewModel :ViewModel(){
    private val mutableCityLiveData = MutableLiveData<String>()
    val cityLiveData: LiveData<String> get() = mutableCityLiveData

    fun selectCity(city: String) {
        mutableCityLiveData.value = city
    }

}