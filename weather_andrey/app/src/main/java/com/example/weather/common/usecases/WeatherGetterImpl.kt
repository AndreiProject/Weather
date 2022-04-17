package com.example.weather.common.usecases

import com.example.weather.common.usecases.models.Weather
import com.example.weather.common.extension.getLocal

class WeatherGetterImpl(
    private val repository: WeatherRepository,
) : WeatherGetter {

    override suspend fun invoke(townId: String, cnt: Int): Weather {
        val lang = getLocal().language
        return repository.getWeather(townId, cnt, lang)
    }
}