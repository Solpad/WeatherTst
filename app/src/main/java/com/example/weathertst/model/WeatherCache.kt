package com.example.weathertst.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather_tables")
data class WeatherCache (

        @PrimaryKey(autoGenerate = false)
        @ColumnInfo        val name: String = "",
        @ColumnInfo
        val temp: String = ""
): Serializable