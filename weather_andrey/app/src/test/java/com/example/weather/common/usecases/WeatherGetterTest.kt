package com.example.weather.common.usecases

import com.example.weather.common.network.repository.*
import com.example.weather.common.network.repository.models.*
import com.example.weather.common.network.*
import com.example.weather.common.network.repositoryMock_GetWeatherMethod_ThenReturn
import com.example.weather.common.extension.DAY_FULL_TIME_PATTERN
import com.example.weather.common.extension.convertToDate
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*
import org.mockito.*
import org.mockito.MockitoAnnotations.openMocks

class WeatherGetterTest {

    @BeforeEach
    fun setUp() { openMocks(this) }

    @Mock
    private lateinit var repository: WeatherRepositoryImpl

    @Test
    fun `check format precipitation weather rain and snow is null`(): Unit = runBlocking {
        val weatherInfo = getWeatherInfoModel(rain = null, snow = null)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val weatherGetter = WeatherGetterImpl(repository)
        val weather = weatherGetter(ANY_STRING, ANY_INT)
        val weatherTime = weather.daysWeather.values.map { it }[0][0]

        assertEquals("0,0 mm", weatherTime.precipitation)
    }

    @Test
    fun `check format precipitation weather snow is null`(): Unit = runBlocking {
        val pr = Precipitation(0.777)
        val weatherInfo = getWeatherInfoModel(rain = pr, snow = null)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val weatherGetter = WeatherGetterImpl(repository)
        val weather = weatherGetter(ANY_STRING, ANY_INT)
        val weatherTime = weather.daysWeather.values.map { it }[0][0]

        assertEquals("0,8 mm", weatherTime.precipitation)
    }

    @Test
    fun `check format precipitation weather rain is null`(): Unit = runBlocking {
        val pr = Precipitation(0.777)
        val weatherInfo = getWeatherInfoModel(rain = null, snow = pr)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val weatherGetter = WeatherGetterImpl(repository)
        val weather = weatherGetter(ANY_STRING, ANY_INT)
        val weatherTime = weather.daysWeather.values.map { it }[0][0]

        assertEquals("0,8 mm", weatherTime.precipitation)
    }

    @Test
    fun `check format date weather`(): Unit = runBlocking {
        val date = "2021-12-02 09:00:00"
        val weatherInfo = getWeatherInfoModel(dateText = date)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val weatherGetter = WeatherGetterImpl(repository)
        val weather = weatherGetter(ANY_STRING, ANY_INT)
        val weatherTime = weather.daysWeather.values.map { it }[0][0]

        assertEquals(date.convertToDate(DAY_FULL_TIME_PATTERN), weatherTime.date)
    }
}