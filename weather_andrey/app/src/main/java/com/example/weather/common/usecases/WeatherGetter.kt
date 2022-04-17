package com.example.weather.common.usecases

import com.example.weather.common.usecases.models.Weather

interface WeatherGetter {
    suspend operator fun invoke(townId: String, cnt: Int): Weather
}