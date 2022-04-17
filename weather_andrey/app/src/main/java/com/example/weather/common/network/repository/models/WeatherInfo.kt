package com.example.weather.common.network.repository.models

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    val dt: Long,
    val wind: Wind,
    val main: Main,
    val weather: List<State>,
    @SerializedName("dt_txt")
    val dateText: String,
    val rain: Precipitation? = null,
    val snow: Precipitation? = null
)