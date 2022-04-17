package com.example.weather.daytime.asserts

import com.example.weather.daytime.screens.DayTimeWeatherViewModel
import org.junit.jupiter.api.Assertions.assertEquals

internal fun assertVmStateEquals(
    model: DayTimeWeatherViewModel,
    expectedState: State
) {
    with(model) {
        assertEquals(town.value, expectedState.town)
        assertEquals(updateDaysWeatherStateProcess.value, expectedState.updateDaysWeatherStateProcess)
        assertEquals(weatherDayTime.value, expectedState.weatherByDays)
    }
}