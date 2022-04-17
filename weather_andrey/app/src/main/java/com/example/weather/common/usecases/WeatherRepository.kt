package com.example.weather.common.usecases

import com.example.weather.common.usecases.models.Weather

interface WeatherRepository {
    suspend fun getWeather(townId: String, cnt: Int, lang: String): Weather
}