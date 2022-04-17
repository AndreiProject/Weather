package com.example.weather.common.di.module

import com.example.weather.common.data.repository.Repository
import com.example.weather.common.domain.*
import dagger.*

@Module
class UseCaseModule {

    @Provides
    fun provideWeatherUseCase(repository: Repository):
            WeatherUseCaseContract = WeatherUseCase(repository)
}