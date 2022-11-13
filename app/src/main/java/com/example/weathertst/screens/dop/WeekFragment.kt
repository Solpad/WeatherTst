package com.example.weathertst.screens.dop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertst.adapters.WeekWeatherAdapters
import com.example.weathertst.databinding.FragmentWeekBinding
import com.example.weathertst.screens.main.MainFragmentViewModel
import com.example.weathertst.utils.Resource
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_week.*
import kotlinx.android.synthetic.main.fragment_week.view.*
import kotlin.properties.Delegates

class WeekFragment : Fragment() {

    private var _binding: FragmentWeekBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: WeekFragmentViewModel
    lateinit var adapters: WeekWeatherAdapters

    private lateinit var currentCity: String
    private var currentFeeling by Delegates.notNull<Double>()
    private var currentTemp by Delegates.notNull<Double>()
    private var currentPressure by Delegates.notNull<Int>()
    private var currentHumidity by Delegates.notNull<Int>()
    private var currentWindSpeed by Delegates.notNull<Double>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeekBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialization(){
        mViewModel = ViewModelProvider(this)[WeekFragmentViewModel::class.java]

        mViewModel.cityLiveData.value = "Moscow"
        mViewModel.getWeekWeather()
        mViewModel.weekWeather.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        adapters = WeekWeatherAdapters(it.list)
                        week_weathers_recycler.adapter = adapters
                        week_weathers_recycler.layoutManager = LinearLayoutManager(context)
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(activity, "Произошла ошибка: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        } )
    }
/*
    private fun initialization(){
        mBinding.nameCity.text = currentCity
        mBinding.feeling.text = "Ощущается как: " + currentFeeling.toString()
        mBinding.temp.text = "Температура: " + currentTemp.toString()
        mBinding.pressure.text = "Давление: " + currentPressure.toString()
        mBinding.humidity.text = "Влажность: " + currentHumidity.toString()
        mBinding.windSpeed.text = "Скорость ветра: " + currentWindSpeed.toString()
    }

    private fun setValueBundle(){
        currentCity = arguments?.getSerializable("name") as String
        currentFeeling = arguments?.getSerializable("feeling") as Double
        currentTemp = arguments?.getSerializable("temp") as Double
        currentPressure = arguments?.getSerializable("pressure") as Int
        currentHumidity = arguments?.getSerializable("humidity") as Int
        currentWindSpeed = arguments?.getSerializable("windSpeed") as Double
    }
 */
}