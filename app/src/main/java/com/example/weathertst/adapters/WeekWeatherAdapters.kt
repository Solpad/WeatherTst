package com.example.weathertst.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertst.R
import com.example.weathertst.model.weekWeather.InfoWeek

class WeekWeatherAdapters(val weeksWeather:List<InfoWeek>): RecyclerView.Adapter<WeekWeatherAdapters.WeekWeatherViewHolder>() {

     class WeekWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         var texts = view.findViewById<TextView>(R.id.temp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context) .inflate(R.layout.week_weather_item, parent, false)
        return WeekWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        val week = weeksWeather[position]
        holder.texts.text = week.main.temp.toString()
    }

    override fun getItemCount(): Int {
        return weeksWeather.size
    }

}