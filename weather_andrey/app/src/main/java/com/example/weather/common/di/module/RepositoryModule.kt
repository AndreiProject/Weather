package com.example.weather.common.di.module

import com.example.weather.common.data.repository.*
import com.example.weather.common.data.repository.service.Service
import com.example.weather.common.data.source.Client
import dagger.*
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideService(): Service = Client.getService()

    @Singleton
    @Provides
    fun provideRepository(service: Service): Repository = AppRepository(service)
}