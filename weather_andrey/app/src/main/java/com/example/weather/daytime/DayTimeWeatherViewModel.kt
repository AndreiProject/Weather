package com.example.weather.daytime

import android.util.Log
import androidx.lifecycle.*
import com.example.weather.common.usecases.*
import com.example.weather.common.usecases.models.*
import com.example.weather.days.DaysWeatherViewModel
import com.example.weather.days.DaysWeatherViewModel.Companion.WEATHER_DAY_INFO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

private val TAG = DaysWeatherViewModel::class.simpleName

class DayTimeWeatherViewModel @Inject constructor(
    private val weatherGetter: WeatherGetter
) : ViewModel() {

    private val _town = MutableStateFlow("")
    val town: StateFlow<String> = _town.asStateFlow()

    private val _weatherDayTime = MutableStateFlow<List<WeatherTime>>(listOf())
    val weatherDayTime: StateFlow<List<WeatherTime>> = _weatherDayTime.asStateFlow()

    private val _updateDaysWeatherStateProcess = MutableStateFlow(false)
    val updateDaysWeatherStateProcess: StateFlow<Boolean> = _updateDaysWeatherStateProcess.asStateFlow()


    fun updateDayTimeWeatherState(townId: String, dayPos: Int) {
        _updateDaysWeatherStateProcess.value = true

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            try {
                val weather = weatherGetter(townId, WEATHER_DAY_INFO * (dayPos + 1))
                updateValuesData(weather, dayPos)
                _updateDaysWeatherStateProcess.value = false
            } catch (ex: Exception) {
                Log.e(TAG, ex.toString())
                _updateDaysWeatherStateProcess.value = false
            }
        }
    }

    private fun updateValuesData(weather: Weather, dayPos: Int) {
        _town.value = weather.townName

        val data = weather.daysWeather.values.toMutableList()
        if (data.size > dayPos) {
            val currentTime = Calendar.getInstance().time
            _weatherDayTime.value = data[dayPos].filter { it.date > currentTime }
        }
    }
}