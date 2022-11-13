package com.example.weathertst.screens.dop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weathertst.databinding.FragmentDopBinding
import kotlin.properties.Delegates

class DopFragment : Fragment() {

    private var _binding: FragmentDopBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: DopFragmentViewModel
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
        _binding = FragmentDopBinding.inflate(layoutInflater,container,false)
        setValueBundle()

        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization(){
        mBinding.nameCity.text = currentCity
        mBinding.feeling.text = "Ощущается как: " + currentFeeling.toString()
        mBinding.temp.text = "Температура: " + currentTemp.toString()
        mBinding.pressure.text = "Давление: " + currentPressure.toString()
        mBinding.humidity.text = "Влажность: " + currentHumidity.toString()
        mBinding.windSpeed.text = "Скорость ветра: " + currentWindSpeed.toString()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setValueBundle(){
        currentCity = arguments?.getSerializable("name") as String
        currentFeeling = arguments?.getSerializable("feeling") as Double
        currentTemp = arguments?.getSerializable("temp") as Double
        currentPressure = arguments?.getSerializable("pressure") as Int
        currentHumidity = arguments?.getSerializable("humidity") as Int
        currentWindSpeed = arguments?.getSerializable("windSpeed") as Double

    }
}