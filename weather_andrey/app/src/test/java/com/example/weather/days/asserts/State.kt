package com.example.weather.days.asserts

import com.example.weather.common.usecases.models.WeatherTime

internal data class State(
    val town: String = "",
    val temperature: String = "",
    val description: String = "",
    val weatherImage: String = "",
    val updateDaysWeatherStateProcess: Boolean = false,
    val weatherByDays: List<WeatherTime> = listOf()
)