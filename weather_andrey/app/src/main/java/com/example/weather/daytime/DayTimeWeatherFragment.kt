package com.example.weather.daytime

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import com.example.weather.databinding.FragmentDayTimeWeatherBinding
import com.example.weather.common.usecases.models.WeatherTime
import com.example.weather.common.fragments.BindingFragment
import com.example.weather.common.extension.*
import com.example.weather.days.DaysWeatherFragment.Companion.TOWN_ID
import com.mikepenz.fastadapter.adapters.FastItemAdapter

class DayTimeWeatherFragment : BindingFragment<FragmentDayTimeWeatherBinding>(
    FragmentDayTimeWeatherBinding::inflate
) {
    private val townId get() = arguments?.getString(DAY_TOWN_KEY) ?: TOWN_ID
    private val dayPos get() = arguments?.getInt(DAY_POS_KEY) ?: 0

    private val viewModel by viewModels<DayTimeWeatherViewModel>() { getViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.updateDayTimeWeatherState(townId, dayPos)
        }
        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
            weatherRv.adapter = FastItemAdapter<DayTimeWeatherItemAdapter>()

            viewModel.town.observe(viewLifecycleOwner) { toolbar.title = it }
            viewModel.weatherDayTime.observe(viewLifecycleOwner) { setData(it) }
            viewModel.updateDaysWeatherStateProcess.observe(viewLifecycleOwner) {
                swipeContainer.isRefreshing = it
            }
            swipeContainer.setOnRefreshListener {
                viewModel.updateDayTimeWeatherState(townId, dayPos)
            }
        }
    }

    private fun setData(weatherTimeItems: List<WeatherTime>) {
        val items = weatherTimeItems.map { wt -> DayTimeWeatherItemAdapter(wt) }
        binding.weatherRv
            .getFastAdapter<DayTimeWeatherItemAdapter>()
            .setItems(items)
    }

    companion object {
        const val DAY_TOWN_KEY = "day.time.weather.town.key"
        const val DAY_POS_KEY = "day.time.weather.day.pos.key"
    }
}