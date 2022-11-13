package com.example.weathertst.screens.main
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weathertst.R
import com.example.weathertst.databinding.FragmentMainBinding
import com.example.weathertst.utils.APP_ACTIVITY
import com.example.weathertst.utils.Resource
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
    }

    override fun onStart() {
        super.onStart()
        setupListeners()
        initialization()
        startWeather()
    }

    private fun setupListeners(){
        btn_search.setOnClickListener { onButtonSearchClick() }
        btn_more.setOnClickListener { onButtonWeekWeatherClick() }
    }

    @SuppressLint("SetTextI18n")
    private fun initialization() {
        mViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        mViewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            Log.e("crush","crush1")
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        weatherDegrees.text = it.main?.let { it1 -> Math.round(it1.temp) }.toString() + "°С"
                        nameCity.text = it.name
                        weatherCondition.text = it.weather[0].description
                        feeling.text = "Ощущается как: " + it.main?.let { it1 -> Math.round(it1.feels_like) }.toString() + "°С"
                        pressure.text = "Давление: " + it.main?.pressure.toString() + " мм рт.ст."
                        humidity.text = "Влажность: " + it.main?.humidity.toString() + " %"
                        windSpeed.text = "Скорость ветра: " + it.wind?.speed.toString() +" м/c"
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

    private fun startWeather(){
        mViewModel.cityLiveData.value = "Moscow"
        mViewModel.getCurrentWeather()
    }

    private fun onButtonSearchClick(){
        mViewModel.cityLiveData.value = mBinding.searchLable.text.toString()
        mViewModel.getCurrentWeather()
        mBinding.searchLable.setText("")
    }

    private fun onButtonWeekWeatherClick(){

        var bundle = Bundle()
        mViewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        bundle.putSerializable("name",it.name)
                        bundle.putSerializable("feeling",it.main?.feels_like)
                        bundle.putSerializable("temp",it.main?.temp)
                        bundle.putSerializable("pressure",it.main?.pressure)
                        bundle.putSerializable("humidity",it.main?.humidity)
                        bundle.putSerializable("windSpeed", it.wind?.speed)
                    }
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(activity, "Произошла ошибка отправки: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        })
        APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_dopFragment,bundle)
    }
}



