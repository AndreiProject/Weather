package com.example.weather.common.domain

import com.example.weather.common.data.repository.*
import com.example.weather.common.domain.model.Weather
import com.example.weather.common.utils.getLocal

class WeatherUseCase(
    private val repository: Repository
) : WeatherUseCaseContract {

    override suspend fun getWeather(townId: String, cnt: Int): Weather {
        val lang = getLocal().language
        return repository.getWeather(townId, cnt, lang)
    }
}