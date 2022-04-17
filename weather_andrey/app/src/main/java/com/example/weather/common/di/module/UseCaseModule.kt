package com.example.weather.common.di.module

import com.example.weather.common.usecases.WeatherRepository
import com.example.weather.common.usecases.*
import dagger.*

@Module
class UseCaseModule {

    @Provides
    fun provideWeatherUseCase(repository: WeatherRepository):
            WeatherGetter = WeatherGetterImpl(repository)
}