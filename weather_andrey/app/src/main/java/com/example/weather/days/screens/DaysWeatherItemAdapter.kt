package com.example.weather.days.screens

import android.view.*
import com.example.weather.R
import com.example.weather.databinding.DaysWeatherItemBinding
import com.example.weather.common.domain.model.WeatherTime
import com.example.weather.common.utils.*
import com.mikepenz.fastadapter.binding.AbstractBindingItem

open class DaysWeatherItemAdapter(
    val weather: WeatherTime,
) : AbstractBindingItem<DaysWeatherItemBinding>() {
    override var identifier: Long = weather.hashCode().toLong()

    override val type: Int
        get() = R.id.days_w_id

    override fun bindView(binding: DaysWeatherItemBinding, payloads: List<Any>) = with(binding) {
        windTv.text = root.context.getString(R.string.days_weather_item_wind, weather.windSpeed)
        pressureTv.text = root.context.getString(R.string.days_weather_item_pressure, weather.pressure)
        temperatureTv.text = root.context.getString(R.string.weather_temperature, weather.temp)
        dateTv.text = weather.date.formatToString(DAY_OF_WEEK_PATTERN).lowercase()
        precipitationTv.text = String.format("%s  %s", weather.description, weather.precipitation)
        weatherIm.loadByUrl(weather.icon)
    }

    override fun unbindView(binding: DaysWeatherItemBinding) = with(binding) {
        windTv.text = null
        pressureTv.text = null
        temperatureTv.text = null
        dateTv.text = null
        precipitationTv.text = null
        weatherIm.setImageDrawable(null)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?,
    ) = DaysWeatherItemBinding.inflate(inflater, parent, false)
}