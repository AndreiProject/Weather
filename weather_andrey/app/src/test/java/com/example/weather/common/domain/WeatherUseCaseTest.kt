package com.example.weather.common.domain

import com.example.weather.common.data.repository.*
import com.example.weather.common.data.repository.model.*
import com.example.weather.common.data.*
import com.example.weather.common.data.repositoryMock_GetWeatherMethod_ThenReturn
import com.example.weather.common.utils.DAY_FULL_TIME_PATTERN
import com.example.weather.common.utils.convertToDate
import org.junit.jupiter.api.*
import org.mockito.Mockito.*
import org.mockito.*
import org.mockito.MockitoAnnotations.openMocks

class WeatherUseCaseTest {

    @BeforeEach
    fun setUp() {
        openMocks(this)
    }

    @Mock
    private lateinit var repository: AppRepository

    @Test
    fun `check format precipitation weather rain and snow is null`() {
        val weatherInfo = getWeatherInfoModel(rain = null, snow = null)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val useCase = WeatherUseCase(repository)
        val testSubscriber = useCase.getWeather(anyString(), anyInt())
            .map { it.daysWeather }
            .map { it.values }
            .map { it.toList() }
            .map { it[0] }
            .map { it[0] }
            .map { it.precipitation }
            .test()

        testSubscriber.await()
        testSubscriber.assertValue("0,0 mm")
    }

    @Test
    fun `check format precipitation weather snow is null`() {
        val pr = Precipitation(0.777)
        val weatherInfo = getWeatherInfoModel(rain = pr, snow = null)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val useCase = WeatherUseCase(repository)
        val testSubscriber = useCase.getWeather(anyString(), anyInt())
            .map { it.daysWeather }
            .map { it.values }
            .map { it.toList() }
            .map { it[0] }
            .map { it[0] }
            .map { it.precipitation }
            .test()

        testSubscriber.await()
        testSubscriber.assertValue("0,8 mm")
    }

    @Test
    fun `check format precipitation weather rain is null`() {
        val pr = Precipitation(0.777)
        val weatherInfo = getWeatherInfoModel(rain = null, snow = pr)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val useCase = WeatherUseCase(repository)
        val testSubscriber = useCase.getWeather(anyString(), anyInt())
            .map { it.daysWeather }
            .map { it.values }
            .map { it.toList() }
            .map { it[0] }
            .map { it[0] }
            .map { it.precipitation }
            .test()

        testSubscriber.await()
        testSubscriber.assertValue("0,8 mm")
    }

    @Test
    fun `check format date weather`() {
        val date = "2021-12-02 09:00:00"
        val weatherInfo = getWeatherInfoModel(dateText = date)
        val result = getResponseModel(list = listOf(weatherInfo))
        repositoryMock_GetWeatherMethod_ThenReturn(repository, result)

        val useCase = WeatherUseCase(repository)
        val testSubscriber = useCase.getWeather(anyString(), anyInt())
            .map { it.daysWeather }
            .map { it.values }
            .map { it.toList() }
            .map { it[0] }
            .map { it[0] }
            .map { it.date }
            .test()

        testSubscriber.await()
        testSubscriber.assertValue(date.convertToDate(DAY_FULL_TIME_PATTERN))
    }
}