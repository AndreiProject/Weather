package com.example.weather.common.di.module

import com.example.weather.common.network.repository.*
import com.example.weather.common.network.repository.Service
import com.example.weather.common.network.source.Client
import com.example.weather.common.usecases.WeatherRepository
import dagger.*
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideService(): Service = Client.getService()

    @Singleton
    @Provides
    fun provideRepository(service: Service): WeatherRepository = WeatherRepositoryImpl(service)
}