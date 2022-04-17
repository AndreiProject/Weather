package com.example.weather.daytime.asserts

import com.example.weather.common.domain.model.WeatherTime

internal data class State(
    val town: String = "",
    val updateDaysWeatherStateProcess: Boolean = false,
    val weatherByDays: List<WeatherTime> = listOf()
)