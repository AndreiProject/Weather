package com.example.weather.daytime.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import com.example.weather.databinding.FragmentDayTimeWeatherBinding
import com.example.weather.common.domain.model.WeatherTime
import com.example.weather.common.utils.*
import com.example.weather.days.screens.DaysWeatherFragment.Companion.TOWN_ID
import com.mikepenz.fastadapter.adapters.FastItemAdapter

class DayTimeWeatherFragment : Fragment() {
    private var _binding: FragmentDayTimeWeatherBinding? = null
    private val binding get() = _binding!!

    private val townId get() = arguments?.getString(DAY_TOWN_KEY) ?: TOWN_ID
    private val dayPos get() = arguments?.getInt(DAY_POS_KEY) ?: 0

    private val viewModel by viewModels<DayTimeWeatherViewModel>() { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDayTimeWeatherBinding
        .inflate(layoutInflater, container, false)
        .also { _binding = it }
        .root

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

    private fun setData(list: List<WeatherTime>) {
        val data = list.map { wt -> DayTimeWeatherItemAdapter(wt) }
        binding.weatherRv
            .getFastAdapter(DayTimeWeatherItemAdapter::class.java)
            .setData(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.weatherRv.adapter = null
        _binding = null
    }

    companion object {
        const val DAY_TOWN_KEY = "day.time.weather.town.key"
        const val DAY_POS_KEY = "day.time.weather.day.pos.key"
    }
}