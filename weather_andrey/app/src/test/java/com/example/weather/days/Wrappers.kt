package com.example.weather.days

import com.example.weather.common.data.*
import com.example.weather.common.domain.*
import com.example.weather.common.data.repository.AppRepository
import com.example.weather.days.asserts.State

internal const val TOWN = "Тамбов"
internal const val TEMP_MAX = 2.0
internal const val TEMP_MIN = TEMP_MAX - 1.0
internal const val DATE_TEXT = "2021-12-02 09:00:00"
internal const val DATE_TEXT_NEXT_DAY = "2021-12-03 09:00:00"
internal const val UPDATE_STATE_PROCESS = false
internal const val UPDATE_VM_DELAY = 1100L

internal fun getDaysWeatherVMStatePostUpdate(): State {
    val list = listOf(
        getWeatherTime(TEMP_MAX.toInt(), DATE_TEXT),
        getWeatherTime(TEMP_MAX.toInt(), DATE_TEXT_NEXT_DAY)
    )
    return State(
        town = TOWN,
        temperature = TEMP_MAX.toInt().toString(),
        description = DESCRIPTION,
        weatherImage = IMG_URL,
        updateDaysWeatherStateProcess = UPDATE_STATE_PROCESS,
        weatherByDays = list
    )
}

internal fun getUseCase(repository: AppRepository): WeatherUseCaseContract {
    val weatherInfoTempMax = getWeatherInfoModel(temp = TEMP_MAX, dateText = DATE_TEXT)
    val weatherInfoTempMin = getWeatherInfoModel(temp = TEMP_MIN, dateText = DATE_TEXT)

    val weatherInfoTempMaxNextDay = getWeatherInfoModel(temp = TEMP_MAX, dateText = DATE_TEXT_NEXT_DAY)
    val weatherInfoTempMinNextDay = getWeatherInfoModel(temp = TEMP_MIN, dateText = DATE_TEXT_NEXT_DAY)

    val listWeatherInfo = listOf(
        weatherInfoTempMax, weatherInfoTempMin,
        weatherInfoTempMaxNextDay, weatherInfoTempMinNextDay
    )

    val result = getResponseModel(town = TOWN, list = listWeatherInfo)
    repositoryMock_GetWeatherMethod_ThenReturn(repository, result)
    return WeatherUseCase(repository)
}

internal fun getUseCase_ThrowError(repository: AppRepository): WeatherUseCaseContract {
    repositoryMock_GetWeatherMethod_ThenReturn_BodyIsNull(repository)
    return WeatherUseCase(repository)
}