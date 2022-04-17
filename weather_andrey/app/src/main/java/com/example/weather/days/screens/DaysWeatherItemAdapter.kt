package com.example.weather.days.screens

import android.view.*
import com.example.weather.R
import com.example.weather.databinding.DaysWeatherItemBinding
import com.example.weather.common.domain.model.WeatherTime
import com.example.weather.common.utils.loadByUrl
import com.example.weather.common.utils.DAY_OF_WEEK_PATTERN
import com.example.weather.common.utils.formatToString
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import java.util.*

open class DaysWeatherItemAdapter(
    val weather: WeatherTime
) : AbstractBindingItem<DaysWeatherItemBinding>() {

    override var identifier: Long = weather.hashCode().toLong()

    override val type: Int
        get() = R.id.days_w_id

    override fun bindView(
        binding: DaysWeatherItemBinding,
        payloads: List<Any>
    ) {
        super.bindView(binding, payloads)
        with(binding) {
            val context = binding.root.context
            windTv.text =
                context.getString(R.string.days_weather_item_wind, weather.windSpeed)
            pressureTv.text =
                context.getString(R.string.days_weather_item_pressure, weather.pressure)
            temperatureTv.text =
                context.getString(R.string.weather_temperature, weather.temp)
            dateTv.text =
                weather.date.formatToString(DAY_OF_WEEK_PATTERN).lowercase(Locale.getDefault())
            precipitationTv.text =
                String.format("%s  %s", weather.description, weather.precipitation)
            weatherIm.loadByUrl(weather.icon)
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = DaysWeatherItemBinding
        .inflate(inflater, parent, false)
}