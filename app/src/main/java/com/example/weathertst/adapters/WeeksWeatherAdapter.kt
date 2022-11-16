package com.example.weathertst.adapters

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathertst.model.weeksWeather.ItemWeekWeather
import java.text.SimpleDateFormat
import java.util.*


class WeeksWeatherAdapters(val weeksWeather:List<ItemWeekWeather>,val context:Context): RecyclerView.Adapter<WeeksWeatherAdapters.WeeksWeatherViewHolder>() {

    class WeeksWeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var icon = view.findViewById<ImageView>(com.example.weathertst.R.id.image_weather)
        var date = view.findViewById<TextView>(com.example.weathertst.R.id.date)
        var temp = view.findViewById<TextView>(com.example.weathertst.R.id.temp)
        var temp_feel = view.findViewById<TextView>(com.example.weathertst.R.id.temp_feel)
        var speed = view.findViewById<TextView>(com.example.weathertst.R.id.windSpeed)
        var humidity = view.findViewById<TextView>(com.example.weathertst.R.id.humidity)
        var pressure = view.findViewById<TextView>(com.example.weathertst.R.id.pressure)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeksWeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context) .inflate(com.example.weathertst.R.layout.week_weather_item, parent, false)
        return WeeksWeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeeksWeatherViewHolder, position: Int) {

        val week = weeksWeather[position]

        val dateNow = Date(week.dt.toLong()*1000)
        val dt1 = SimpleDateFormat("EEEE dd.MM")
        holder.date.text = dt1.format(dateNow)

        val mDrawableName = "ic_" + week.weather[0].icon
        val resID = context.resources.getIdentifier(mDrawableName, "drawable", com.example.weathertst.utils.PACKAGE_NAME )
        holder.icon.setImageResource(resID)

        holder.temp.text = "Температура " + week.temp.day.toString() + "°С"
        holder.temp_feel.text = "Ощущается как "+week.feels_like.day.toString() + "°С"
        holder.pressure.text = "Давление "+week.pressure.toString() + " гПа"
        holder.humidity.text = "Влажность "+week.humidity.toString() + " %"
        holder.speed.text = "Скорость ветра " + week.speed.toString() +" м/с"
    }

    override fun getItemCount(): Int {
        return weeksWeather.size
    }

}