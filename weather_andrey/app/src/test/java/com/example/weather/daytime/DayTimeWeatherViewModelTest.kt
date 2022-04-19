package com.example.weather.daytime

import com.example.weather.common.network.repository.WeatherRepositoryImpl
import com.example.weather.daytime.asserts.*
import com.example.weather.daytime.DayTimeWeatherViewModel
import com.example.weather.common.InstantExecutorExtension
import com.example.weather.daytime.DAY_POS
import com.example.weather.daytime.TOWN
import com.example.weather.daytime.UPDATE_VM_DELAY
import com.example.weather.daytime.getDayTimeWeatherVMStatePostUpdate
import com.example.weather.daytime.getUseCase
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.mockito.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations.openMocks

@ExtendWith(InstantExecutorExtension::class)
class DayTimeWeatherViewModelTest {

    @Mock
    private lateinit var repository: WeatherRepositoryImpl

    @BeforeEach
    fun setUp() { openMocks(this) }

    @Test
    fun `check DayTimeWeatherViewModel default state fields`(): Unit = runBlocking {
        val useCase = getUseCase(repository)

        val model = DayTimeWeatherViewModel(useCase)
        val baseState = State()
        assertVmStateEquals(model, baseState)
    }

    @Test
    fun `check DayTimeWeatherViewModel update state fields`(): Unit = runBlocking {
        val useCase = getUseCase(repository)

        val model = DayTimeWeatherViewModel(useCase)
        val baseState = State()
        assertVmStateEquals(model, baseState)

        model.updateDayTimeWeatherState(TOWN, DAY_POS)
        launch {
            delay(UPDATE_VM_DELAY)
            val postUpdateState = getDayTimeWeatherVMStatePostUpdate()
            assertVmStateEquals(model, postUpdateState)
        }
    }
}