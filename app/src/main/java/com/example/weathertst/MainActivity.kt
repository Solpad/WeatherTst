package com.example.weathertst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.weathertst.databinding.ActivityMainBinding
import com.example.weathertst.utils.APP_ACTIVITY

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
/*
        val weatherRepository = WeatherRepository(WeatherDatabase())
        val viewModelProviderFactory = WeatherViewModelProviderFactory(application, weatherRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainFragmentViewModel::class.java)
*/

        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        APP_ACTIVITY = this

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}