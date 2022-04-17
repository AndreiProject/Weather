package com.example.weather.days.screens

import android.util.Log
import androidx.lifecycle.*
import com.example.weather.common.domain.*
import com.example.weather.common.domain.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private val TAG = DaysWeatherViewModel::class.simpleName
private const val DAY_COUNT = 5

class DaysWeatherViewModel @Inject constructor(
    private val useCase: WeatherUseCaseContract
) : ViewModel() {

    private val _town = MutableStateFlow("")
    val town: StateFlow<String> = _town.asStateFlow()

    private val _thisTemperature = MutableStateFlow("")
    val thisTemperature: StateFlow<String> = _thisTemperature.asStateFlow()

    private val _thisDescription = MutableStateFlow("")
    val thisDescription: StateFlow<String> = _thisDescription.asStateFlow()

    private val _thisWeatherImage = MutableStateFlow("")
    val thisWeatherImage: StateFlow<String> = _thisWeatherImage.asStateFlow()

    private val _weatherByDays = MutableStateFlow<List<WeatherTime>>(listOf())
    val weatherByDays: StateFlow<List<WeatherTime>> = _weatherByDays.asStateFlow()

    private val _onErrorMessage: MutableSharedFlow<String> = MutableSharedFlow(extraBufferCapacity = 1)
    val onErrorMessage: SharedFlow<String> = _onErrorMessage.asSharedFlow()

    private val _openFragmentEvent: MutableSharedFlow<Pair<String, Int>> = MutableSharedFlow(extraBufferCapacity = 1)
    val openFragmentEvent: SharedFlow<Pair<String, Int>> = _openFragmentEvent.asSharedFlow()

    private val _updateDaysWeatherStateProcess = MutableStateFlow(false)
    val updateDaysWeatherStateProcess: StateFlow<Boolean> = _updateDaysWeatherStateProcess.asStateFlow()

    fun updateDaysWeatherState(townId: String = town.value) {
        _updateDaysWeatherStateProcess.value = true

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            try {
                val weather = useCase.getWeather(townId, WEATHER_DAY_INFO * DAY_COUNT)
                updateValuesData(weather)
                _updateDaysWeatherStateProcess.value = false
            } catch (ex: Exception) {
                Log.e(TAG, ex.toString())
                _updateDaysWeatherStateProcess.value = false
                ex.message?.let { ms -> _onErrorMessage.tryEmit(ms) }
            }
        }
    }

    private fun updateValuesData(weather: Weather) {
        _town.value = weather.townName

        var isNotUpdate = true
        _weatherByDays.value = weather.daysWeather.values
            .asSequence()
            .take(DAY_COUNT)
            .onEach { dayTime ->
                if (isNotUpdate) {
                    dayTime.firstOrNull()
                        ?.let {
                            _thisTemperature.value = it.temp.toString()
                            _thisDescription.value = it.description
                            _thisWeatherImage.value = it.icon
                            isNotUpdate = false
                        }
                }
            }
            .map { dayTime ->
                dayTime.maxByOrNull { it.temp }
            }
            .filterNotNull()
            .toList()
    }

    fun startOpenFragmentEvent(pos: Int) {
        if (_town.value.isNotBlank()) {
            _openFragmentEvent.tryEmit(Pair(_town.value, pos))
        } else {
            _onErrorMessage.tryEmit("town is empty")
        }
    }

    companion object {
        const val WEATHER_DAY_INFO = 24 / 3
    }
}