package com.example.weather.common.data.repository.model

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val city: City,
    val list: List<WeatherInfo>
)

data class City(
    val id: Long,
    val name: String,
    val country: String,
    val timezone: Long,
)

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

data class Wind(
    val speed: Double,
)

data class Main(
    val temp: Double,
    val pressure: Long,
)

data class State(
    val id: Long,
    val description: String,
    val icon: String
) {
    fun iconConvertToUrl() = "https://openweathermap.org/img/wn/$icon.png"
}

data class Precipitation(
    @SerializedName("3h")
    val height: Double
)