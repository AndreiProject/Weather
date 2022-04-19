package com.example.weather.common.usecases

import com.example.weather.common.usecases.models.WeatherTime
import com.example.weather.common.extension.DAY_FULL_TIME_PATTERN
import com.example.weather.common.extension.convertToDate

internal const val PRESSURE = 1006L
internal const val WIND_SPEED = "5,5"
internal const val DESCRIPTION = "облачно с прояснениями"
internal const val PRECIPITATION = "0,0 mm"
internal const val ICON_TAG = "04d"
internal const val IMG_URL = "https://openweathermap.org/img/wn/$ICON_TAG.png"

internal const val ANY_STRING = ""
internal const val ANY_INT = -1

internal fun getWeatherTime(temp: Int, time: String): WeatherTime {
    return WeatherTime(
        temp,
        WIND_SPEED,
        PRESSURE,
        DESCRIPTION,
        PRECIPITATION,
        IMG_URL,
        time.convertToDate(DAY_FULL_TIME_PATTERN),
    )
}