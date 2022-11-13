package com.example.weathertst.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathertst.R
import com.example.weathertst.databinding.FragmentMainBinding
import com.example.weathertst.model.currentWeather.CurrentWeatherResponse

import com.example.weathertst.utils.APP_ACTIVITY
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    private lateinit var mViewModel: MainFragmentViewModel
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return mBinding.root

        startWeather()

    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        initialization()

    }

    private fun setupListeners(){
        btn_search.setOnClickListener { onButtonSearchClick() }
        btn_more.setOnClickListener { onButtonMoreClick() }

    }

    private fun initialization() {
        mViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)


        val weatherObserver = Observer<CurrentWeatherResponse> {

            weatherDegrees.text = it.main?.let { it1 -> Math.round(it1.temp) }.toString() + "°"
            nameCity.text = it.name
            weatherCondition.text = it.weather[0].main

        }
        mViewModel.myResponse.observe(this, weatherObserver)


    }

    private fun startWeather(){
        mViewModel.cityLiveData.value = "Moscow"
        mViewModel.getCurrentData()
    }

    private fun onButtonSearchClick(){
        mViewModel.cityLiveData.value = mBinding.searchLable.text.toString()

        mViewModel.getCurrentData()

        mBinding.searchLable.setText("")
    }

    private fun onButtonMoreClick(){

        var bundle = Bundle()

        val weatherToDopObserver = Observer<CurrentWeatherResponse> {

            bundle.putSerializable("name",it.name)
            bundle.putSerializable("feeling",it.main?.feels_like)
            bundle.putSerializable("temp",it.main?.temp)
            bundle.putSerializable("pressure",it.main?.pressure)
            bundle.putSerializable("humidity",it.main?.humidity)
            bundle.putSerializable("windSpeed", it.wind?.speed)


        }

        mViewModel.myResponse.observe(this,weatherToDopObserver)
        APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_dopFragment,bundle)
    }

}



