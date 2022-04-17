package com.example.weather.daytime

import com.example.weather.common.network.*
import com.example.weather.common.network.repository.WeatherRepositoryImpl
import com.example.weather.common.network.repositoryMock_GetWeatherMethod_ThenReturn
import com.example.weather.common.usecases.*
import com.example.weather.daytime.asserts.State
import com.example.weather.common.extension.DAY_FULL_TIME_PATTERN
import com.example.weather.common.extension.formatToString
import java.time.*
import java.util.*

internal const val TOWN = "Тамбов"
internal const val DAY_POS = 0
internal const val TEMP = 2.0
internal const val UPDATE_STATE_PROCESS = false
internal const val UPDATE_VM_DELAY = 1100L

private val DATE = getDateTime().plusHours(10).toDate()
internal val DATE_TEXT = DATE.formatToString(DAY_FULL_TIME_PATTERN)

internal fun getDateTime(): LocalDateTime {
    return LocalDateTime.ofInstant(
        Calendar.getInstance().toInstant(),
        ZoneId.systemDefault()
    )
}

internal fun getDayTimeWeatherVMStatePostUpdate(): State {
    return State(
        town = TOWN,
        updateDaysWeatherStateProcess = UPDATE_STATE_PROCESS,
        weatherByDays = listOf(getWeatherTime(TEMP.toInt(), DATE_TEXT))
    )
}

internal suspend fun getUseCase(repository: WeatherRepositoryImpl): WeatherGetter {
    val weatherInfo = getWeatherInfoModel(temp = TEMP, dateText = DATE_TEXT)
    val result = getResponseModel(town = TOWN, list = listOf(weatherInfo))
    repositoryMock_GetWeatherMethod_ThenReturn(repository, result)
    return WeatherGetterImpl(repository)
}