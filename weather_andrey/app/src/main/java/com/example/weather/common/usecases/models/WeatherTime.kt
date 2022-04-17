package com.example.weather.common.usecases.models

import java.util.*

data class WeatherTime(
    val temp: Int,
    val windSpeed: String,
    val pressure: Long,
    val description: String,
    val precipitation: String,
    val icon: String,
    val date: Date,
)