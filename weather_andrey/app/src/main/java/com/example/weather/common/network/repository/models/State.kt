package com.example.weather.common.network.repository.models

data class State(
    val id: Long,
    val description: String,
    val icon: String,
) {
    fun iconConvertToUrl() = "https://openweathermap.org/img/wn/$icon.png"
}