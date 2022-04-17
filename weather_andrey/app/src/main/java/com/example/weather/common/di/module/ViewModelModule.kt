package com.example.weather.common.di.module

import androidx.lifecycle.ViewModel
import com.example.weather.common.di.module.multibinding.map.ViewModelKey
import com.example.weather.days.screens.DaysWeatherViewModel
import com.example.weather.daytime.screens.DayTimeWeatherViewModel
import dagger.*
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(DaysWeatherViewModel::class)]
    fun provideDaysWeatherViewModel(daysWeatherViewModel: DaysWeatherViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DayTimeWeatherViewModel::class)]
    fun provideDayTimeWeatherViewModel(dayTimeWeatherViewModel: DayTimeWeatherViewModel): ViewModel
}