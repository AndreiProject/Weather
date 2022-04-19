package com.example.weather.common.network

import com.example.weather.common.network.repository.Service
import com.example.weather.common.network.repository.WeatherRepositoryImpl
import com.example.weather.common.network.repository.models.*
import com.example.weather.common.usecases.*
import io.reactivex.rxjava3.core.Single
import org.mockito.Mockito
import retrofit2.Response

internal suspend fun repositoryMock_GetWeatherMethod_ThenReturn(
    repository: WeatherRepositoryImpl,
    resultMock: WeatherModel,
) {
    val weather = WeatherRepositoryImpl.convertToWeather(resultMock)
    Mockito.`when`(repository.getWeather(Mockito.anyString(),
        Mockito.anyInt(),
        Mockito.anyString()))
        .thenReturn(weather)
}

internal suspend fun repositoryMock_GetWeatherMethod_ThenReturn_BodyIsNull(): WeatherRepository {
    val result = object : Service {
        override suspend fun getWeather(
            requestUrl: String,
            townId: String,
            keyId: String,
            cnt: Int,
            lang: String,
            units: String,
        ) = getWeatherModel_TestResponse(null)
    }
    return WeatherRepositoryImpl(result)
}

internal fun getWeatherModel_TestResponse(body: WeatherModel?)
        : Response<WeatherModel> = Response.success(body)

internal fun getResponseModel(
    code: String = "0",
    message: Long = 0L,
    cnt: Long = 0L,
    town: String = "",
    list: List<WeatherInfo>,
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