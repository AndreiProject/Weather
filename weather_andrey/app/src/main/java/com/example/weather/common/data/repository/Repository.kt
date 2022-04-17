package com.example.weather.common.data.repository

import com.example.weather.common.domain.model.Weather

interface Repository {
    suspend fun getWeather(townId: String, cnt: Int, lang: String): Weather
}