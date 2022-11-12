package com.example.weathertst.screens.dop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weathertst.databinding.FragmentDopBinding
import com.example.weathertst.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_dop.*
import kotlin.properties.Delegates

class DopFragment : Fragment() {

    private var _binding: FragmentDopBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: DopFragmentViewModel
    private lateinit var currentCity: String
    private var currentFeeling by Delegates.notNull<Float>()
    private var currentTemp by Delegates.notNull<Float>()
    private var currentPressure by Delegates.notNull<Float>()
    private var currentHumidity by Delegates.notNull<Float>()
    private var currentWindSpeed by Delegates.notNull<Float>()


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
        currentFeeling = arguments?.getSerializable("feeling") as Float
        currentTemp = arguments?.getSerializable("temp") as Float
        currentPressure = arguments?.getSerializable("pressure") as Float
        currentHumidity = arguments?.getSerializable("humidity") as Float
        currentWindSpeed = arguments?.getSerializable("windSpeed") as Float

    }
}