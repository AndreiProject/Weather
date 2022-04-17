package com.example.weather.common.domain

import com.example.weather.common.domain.model.Weather

interface WeatherUseCaseContract {
    suspend fun getWeather(townId: String, cnt: Int) : Weather
}