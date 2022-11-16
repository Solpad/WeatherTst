package com.example.weatherapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.models.geocoding.LocationResponseItem
import com.example.weathertst.R
import com.example.weathertst.adapters.WeeksWeatherAdapters
import kotlinx.android.synthetic.main.saved_item.view.*

class SavedAdapter(var list: List<LocationResponseItem>): RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    inner class SavedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.location_in_rv_saved)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.saved_item, parent, false)
        return SavedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val location = list.get(position)
        Log.e("loc",location.name.toString())
        holder.name.text = location.name.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}