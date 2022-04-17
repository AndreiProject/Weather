package com.example.weather.daytime.screens

import android.view.*
import com.example.weather.R
import com.example.weather.databinding.DayTimeWeatherItemBinding
import com.example.weather.common.domain.model.WeatherTime
import com.example.weather.common.utils.*
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class DayTimeWeatherItemAdapter(
    val weather: WeatherTime,
) : AbstractBindingItem<DayTimeWeatherItemBinding>() {
    override var identifier: Long = weather.hashCode().toLong()

    override val type: Int
        get() = R.id.day_tw_id

    override fun bindView(binding: DayTimeWeatherItemBinding, payloads: List<Any>) = with(binding) {
        temperatureTv.text = root.context.getString(R.string.weather_temperature, weather.temp)
        dateTv.text = weather.date.formatToString(DAY_TIME_PATTERN)
        weatherIm.loadByUrl(weather.icon)
    }

    override fun unbindView(binding: DayTimeWeatherItemBinding) = with(binding) {
        temperatureTv.text = null
        dateTv.text = null
        weatherIm.setImageDrawable(null)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
    ) = DayTimeWeatherItemBinding.inflate(inflater, parent, false)
}