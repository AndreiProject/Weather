package com.example.weather.common.network.repository.models

data class WeatherModel(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val city: City,
    val list: List<WeatherInfo>
)