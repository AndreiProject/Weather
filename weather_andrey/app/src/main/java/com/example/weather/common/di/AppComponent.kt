package com.example.weather.common.di

import com.example.weather.common.di.module.*
import com.example.weather.common.di.module.multibinding.map.MultiViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        RepositoryModule::class,
        UseCaseModule::class,
        ViewModelModule::class
    ]
)
@Singleton
interface AppComponent {
    val factory: MultiViewModelFactory
}