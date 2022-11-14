package com.example.weathertst.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertst.R
import com.example.weathertst.model.weekWeather.InfoWeek

class WeekWeatherAdapters(val weeksWeather:List<InfoWeek>): RecyclerView.Adapter<WeekWeatherAdapters.WeekWeatherViewHolder>() {

     class WeekWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
         var date = view.findViewById<TextView>(R.id.date)
         var temp = view.findViewById<TextView>(R.id.temp)
         var temp_feel = view.findViewById<TextView>(R.id.temp_feel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context) .inflate(R.layout.week_weather_item, parent, false)
        return WeekWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) {
        val week = weeksWeather[position]
        holder.date.text = week.dt_txt
        holder.temp.text = "Температура " + week.main.temp.toString() + "°С"
        holder.temp_feel.text = "Ощущается как "+week.main.feels_like.toString() + "°С"
    }

    override fun getItemCount(): Int {
        return weeksWeather.size
    }

}