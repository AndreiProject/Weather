package com.example.weather.days.screen

import com.example.weather.common.data.repository.AppRepository
import com.example.weather.days.asserts.*
import com.example.weather.days.screens.DaysWeatherViewModel
import com.example.weather.common.InstantExecutorExtension
import com.example.weather.days.UPDATE_VM_DELAY
import com.example.weather.days.getDaysWeatherVMStatePostUpdate
import com.example.weather.days.getUseCase
import com.example.weather.days.getUseCase_ThrowError
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.*
import org.mockito.*
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import kotlinx.coroutines.*
import org.junit.jupiter.api.BeforeAll
import org.mockito.MockitoAnnotations.openMocks

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantExecutorExtension::class)
class DaysWeatherViewModelTest {

    @BeforeAll
    fun setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Mock
    private lateinit var repository: AppRepository

    @BeforeEach
    fun setUpBefore() {
        openMocks(this)
    }

    @Test
    fun `check DaysWeatherViewModel default state fields`() {
        val useCase = getUseCase(repository)

        val model = DaysWeatherViewModel(useCase)
        val baseState = State()
        assertVmStateEquals(model, baseState)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `check DaysWeatherViewModel update state fields`(): Unit = runBlocking {
        val useCase = getUseCase(repository)

        val model = DaysWeatherViewModel(useCase)
        val baseState = State()
        assertVmStateEquals(model, baseState)

        model.updateDaysWeatherState()
        launch {
            delay(UPDATE_VM_DELAY)
            val postUpdateState = getDaysWeatherVMStatePostUpdate()
            assertVmStateEquals(model, postUpdateState)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun `check DaysWeatherViewModel update result is error`() = runBlocking {
        val useCase = getUseCase_ThrowError(repository)

        val model = DaysWeatherViewModel(useCase)
        launch() {
            delay(UPDATE_VM_DELAY)
            model.updateDaysWeatherState()
        }

        assertVmPostUpdateOnErrorState(model)
        val baseState = State()
        assertVmStateEquals(model, baseState)
    }
}