package com.example.weather.common.data.repository

import com.example.weather.common.data.repository.model.*
import com.example.weather.common.data.repository.service.Service
import com.example.weather.common.domain.model.*
import com.example.weather.common.utils.*
import java.lang.RuntimeException
import java.util.*

class AppRepository(private val service: Service) : Repository {

    override suspend fun getWeather(townId: String, cnt: Int, lang: String): Weather {
        val requestUrl = "https://api.openweathermap.org/data/2.5/forecast"
        val keyId = "4b79666adea282032f3a6ab120bfe067"
        val units = "metric"

        val response = service.getWeather(requestUrl, townId, keyId, cnt, lang, units)
        val body = response.body() ?: throw RuntimeException("Weather body is null")
        return convertToWeather(body)
    }

    private fun convertToWeather(model: WeatherModel): Weather {
        val townName = model.city.name
        val cl = Calendar.getInstance()
        val daysWeatherMap = model.list.asSequence()
            .map {
                convertToWeatherTime(it)
            }
            .groupBy {
                cl.time = it.date
                cl.get(Calendar.DAY_OF_MONTH)
            }
        return Weather(townName, daysWeatherMap)
    }

    private fun convertToWeatherTime(wi: WeatherInfo): WeatherTime {
        val state = wi.weather.first()
        return WeatherTime(
            description = state.description.lowercase(getLocal()),
            icon = state.iconConvertToUrl(),
            temp = wi.main.temp.toInt(),
            pressure = wi.main.pressure,
            windSpeed = String.format("%.1f", wi.wind.speed),
            precipitation = getPrecipitation(wi),
            date = wi.dateText.convertToDate(DAY_FULL_TIME_PATTERN)
        )
    }

    private fun getPrecipitation(weather: WeatherInfo): String {
        val num = weather.rain?.height ?: weather.snow?.height
        return if (num != null) {
            String.format("%.1f mm", num)
        } else "0,0 mm"
    }
}