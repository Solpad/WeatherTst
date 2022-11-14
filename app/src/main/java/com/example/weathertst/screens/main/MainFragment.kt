package com.example.weathertst.screens.main
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.MainActivity
import com.example.weathertst.R
import com.example.weathertst.databinding.FragmentMainBinding
import com.example.weathertst.repositroty.WeatherMvvmRepo
import com.example.weathertst.utils.APP_ACTIVITY
import com.example.weathertst.utils.Resource
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

class MainFragment : Fragment() {

    private lateinit var mViewModel: MainFragmentViewModel
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val repositoryWeather = WeatherMvvmRepo()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        _binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userLocationInfo()
        setupListeners()
        initialization()

        go_search.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }
    }
    fun userLocationInfo(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)
        if (mViewModel.selectedLocation == null) {
            //isLocationPermissionGranted()
        }
    }

    private fun setupListeners(){
        btn_search.setOnClickListener { onButtonSearchClick() }
        btn_more.setOnClickListener { onButtonWeekWeatherClick() }
    }

    @SuppressLint("SetTextI18n")
    private fun initialization() {
        repositoryWeather.getSelectedLocation().toString()
        Log.e("location ",mViewModel.location.value?.data.toString())
        Log.e("SelectedLocation ",repositoryWeather.getSelectedLocation().toString())
        Log.e("SelectedLocation ",mViewModel.selectedLocation.toString())

        mViewModel.selectedLocation = repositoryWeather.getSelectedLocation() // выбранная локация которую берем из search

        mViewModel.selectedLocation?.observe(viewLifecycleOwner, Observer { location ->
            var generatedLocation = ""
            Log.e("Selected Location","IS NOT NULL!")
            if (location.local_names?.ru != null) {
                generatedLocation += location.local_names.ru
            } else if (location.local_names?.en != null) {
                generatedLocation += location.local_names.en
            } else if (location.name != null) {
                generatedLocation += location.name
            }
            if (location.country != null) {
                generatedLocation += ", " + location.country
            }
            nameCity.text = generatedLocation

            mViewModel.getCurrentWeatherLat(location.lat, location.lon)

        })

        //mViewModel.getCurrentWeatherLat(51.5073219, -0.1276474)
        /*
          mViewModel.currentWeather.observe(viewLifecycleOwner, Observer {

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

         */
        mViewModel.location.observe(viewLifecycleOwner, Observer { it ->
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        mViewModel.getCurrentWeatherLat(it[0].lat,it[0].lon)
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
        })


        mViewModel.currentWeatherLat.observe(viewLifecycleOwner, Observer {
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


    private fun startWeather() {
        mViewModel.cityLiveData.value = "Moscow"
        mViewModel.getCurrentWeather()
    }

    private fun onButtonSearchClick(){
/*
        mViewModel.cityLiveData.value = mBinding.searchLable.text.toString()
        mViewModel.getCurrentWeather()
        mBinding.searchLable.setText("")

 */
    }
    private fun isLocationPermissionGranted() {
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(
                activity as MainActivity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity as MainActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                101
            )
        } else {
            task.addOnSuccessListener {
                if (it != null) {
                    mViewModel.getCurrentWeatherLat(it.latitude, it.longitude)
                }
            }
        }
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



