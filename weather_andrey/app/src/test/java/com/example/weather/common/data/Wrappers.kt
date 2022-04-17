package com.example.weather.common.data

import com.example.weather.common.data.repository.AppRepository
import com.example.weather.common.data.repository.model.*
import com.example.weather.common.domain.*
import io.reactivex.rxjava3.core.Single
import org.mockito.Mockito
import retrofit2.Response

internal fun repositoryMock_GetWeatherMethod_ThenReturn(
    repository: AppRepository,
    resultMock: WeatherModel
) {
    val result = Single.just(getWeatherModel_TestResponse(resultMock))
    Mockito.`when`(repository.getWeather(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(result)
}

internal fun repositoryMock_GetWeatherMethod_ThenReturn_BodyIsNull(
    repository: AppRepository
) {
    val result = Single.just(getWeatherModel_TestResponse(null))
    Mockito.`when`(repository.getWeather(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(result)
}

internal fun getWeatherModel_TestResponse(body: WeatherModel?)
        : Response<WeatherModel> = Response.success(body)

internal fun getResponseModel(
    code: String = "0",
    message: Long = 0L,
    cnt: Long = 0L,
    town: String = "",
    list: List<WeatherInfo>
): WeatherModel {
    val city = City(id = 0, town, country = "RU", timezone = 10800)
    return WeatherModel(code, message, cnt, city, list)
}

internal fun getWeatherInfoModel(
    dt: Long = 0L,
    wind: Wind = Wind(speed = 5.48),
    temp: Double = 0.0,
    description: String = DESCRIPTION,
    icon: String = ICON_TAG,
    dateText: String = "2021-12-02 09:00:00",
    rain: Precipitation? = null,
    snow: Precipitation? = null,
): WeatherInfo {
    val state = State(id = 803, description, icon = icon)
    val weather = listOf(state)
    val main = Main(temp = temp, pressure = 1006)
    return WeatherInfo(dt, wind, main, weather, dateText, rain, snow)
}