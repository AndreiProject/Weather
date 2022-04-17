package com.example.weather.days.asserts

import com.example.weather.days.screens.DaysWeatherViewModel
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.Assertions.*

internal suspend fun assertVmPostUpdateOnErrorState(model: DaysWeatherViewModel) {
    val result = model.onErrorMessage.first()
    assertNotNull(result)
}

internal fun assertVmStateEquals(
    model: DaysWeatherViewModel,
    expectedState: State
) {
    with(model) {
        assertEquals(town.value, expectedState.town)
        assertEquals(thisTemperature.value, expectedState.temperature)
        assertEquals(thisDescription.value, expectedState.description)
        assertEquals(thisWeatherImage.value, expectedState.weatherImage)
        assertEquals(weatherByDays.value, expectedState.weatherByDays)
        assertEquals(updateDaysWeatherStateProcess.value, expectedState.updateDaysWeatherStateProcess)
    }
}