package com.example.weather.common.usecases.models

data class Weather(
    val townName: String,
    val daysWeather: Map<Int, List<WeatherTime>>,
)