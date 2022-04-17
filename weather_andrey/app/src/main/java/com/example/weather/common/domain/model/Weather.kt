package com.example.weather.common.domain.model

import java.util.*

data class Weather(val townName: String, val daysWeather: Map<Int, List<WeatherTime>>)

data class WeatherTime(
    val temp: Int,
    val windSpeed: String,
    val pressure: Long,
    val description: String,
    val precipitation: String,
    val icon: String,
    val date: Date
)