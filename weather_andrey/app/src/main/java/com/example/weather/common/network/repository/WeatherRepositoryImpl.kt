package com.example.weather.common.network.repository

import com.example.weather.common.network.repository.models.*
import com.example.weather.common.usecases.models.*
import com.example.weather.common.extension.*
import com.example.weather.common.usecases.WeatherRepository
import java.lang.RuntimeException
import java.util.*

class WeatherRepositoryImpl(private val service: Service) : WeatherRepository {

    override suspend fun getWeather(townId: String, cnt: Int, lang: String): Weather {
        val requestUrl = "https://api.openweathermap.org/data/2.5/forecast"
        val keyId = "4b79666adea282032f3a6ab120bfe067"
        val units = "metric"

        val response = service.getWeather(requestUrl, townId, keyId, cnt, lang, units)
        val body = response.body() ?: throw RuntimeException("Weather body is null")
        return convertToWeather(body)
    }

    companion object {
        fun convertToWeather(model: WeatherModel): Weather {
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
}