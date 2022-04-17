package com.example.weather.days.screens

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.example.weather.*
import com.example.weather.databinding.FragmentDaysWeatherBinding
import com.example.weather.common.domain.model.WeatherTime
import com.example.weather.common.utils.*
import com.example.weather.days.screens.DialogSearchFragment.Companion.TOWN_KEY
import com.example.weather.days.screens.DialogSearchFragment.Companion.REQUEST_KEY
import com.example.weather.days.utils.doOnEnd
import com.example.weather.daytime.screens.DayTimeWeatherFragment.Companion.DAY_POS_KEY
import com.example.weather.daytime.screens.DayTimeWeatherFragment.Companion.DAY_TOWN_KEY
import com.mikepenz.fastadapter.adapters.FastItemAdapter

private const val APP_BAR_LAYOUT_EXPANDED_KEY = "app.bar.layout.is.expanded.key"

class DaysWeatherFragment : Fragment() {
    private var _binding: FragmentDaysWeatherBinding? = null
    private val binding get() = _binding!!

    private var appBarLayoutIsExpanded = true

    private val viewModel: DaysWeatherViewModel by viewModels { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.updateDaysWeatherState(TOWN_ID)
        } else {
            appBarLayoutIsExpanded =
                savedInstanceState.getBoolean(APP_BAR_LAYOUT_EXPANDED_KEY, appBarLayoutIsExpanded)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentDaysWeatherBinding
        .inflate(layoutInflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            weatherRv.adapter = FastItemAdapter<DaysWeatherItemAdapter>()
                .apply {
                    onClickListener = { _, _, _, dayPos ->
                        viewModel.startOpenFragmentEvent(dayPos)
                        true
                    }
                }

            swipeContainer.setOnRefreshListener { viewModel.updateDaysWeatherState() }
            searchIm.setOnClickListener { DialogSearchFragment.showDialog(this@DaysWeatherFragment) }

            viewModel.town.observe(viewLifecycleOwner) { binding.title.text = it }
            viewModel.thisDescription.observe(viewLifecycleOwner) { precipitationTv.text = it }
            viewModel.thisWeatherImage.observe(viewLifecycleOwner) { weatherIm.loadByUrl(it) }
            viewModel.weatherByDays.observe(viewLifecycleOwner) { setItems(it) }
            viewModel.onErrorMessage.observe(viewLifecycleOwner) { showMessage(it) }
            viewModel.openFragmentEvent.observe(viewLifecycleOwner) { openTimeWeatherFragment(it) }
            viewModel.thisTemperature.observe(viewLifecycleOwner) {
                temperatureTv.text = if (it.isNotBlank())
                    getString(R.string.weather_temperature_sr_format, it) else it
            }
            viewModel.updateDaysWeatherStateProcess.observe(viewLifecycleOwner) {
                swipeContainer.isRefreshing = it
            }

            val state = if (appBarLayoutIsExpanded) R.id.start else R.id.end
            motionRoot.jumpToState(state)
            motionRoot.doOnEnd { appBarLayoutIsExpanded = it == R.id.start }

            childFragmentManager.setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner)
            { _, bundle -> searchDialogProcessingResult(bundle) }
        }
    }

    private fun openTimeWeatherFragment(data: Pair<String, Int>) {
        getNavController().navigate(
            R.id.navigationToDayTimeWeatherFragment,
            bundleOf(DAY_TOWN_KEY to data.first, DAY_POS_KEY to data.second)
        )
    }

    private fun setItems(weatherTimeItems: List<WeatherTime>) {
        val items = weatherTimeItems.map { wt -> DaysWeatherItemAdapter(wt) }
        binding.weatherRv
            .getFastAdapter<DaysWeatherItemAdapter>()
            .setItems(items)
    }

    private fun searchDialogProcessingResult(bundle: Bundle) {
        val result = bundle.getString(TOWN_KEY, null)
        result?.let {
            viewModel.updateDaysWeatherState(result)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(APP_BAR_LAYOUT_EXPANDED_KEY, appBarLayoutIsExpanded)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        childFragmentManager.clearFragmentResultListener(REQUEST_KEY)
        binding.weatherRv.adapter = null
        _binding = null
    }

    companion object {
        const val TOWN_ID = "Тамбов"
    }
}