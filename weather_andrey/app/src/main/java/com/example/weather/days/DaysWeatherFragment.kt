package com.example.weather.days

import android.os.Bundle
import android.view.*
import com.example.weather.common.extension.*
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.example.weather.*
import com.example.weather.databinding.FragmentDaysWeatherBinding
import com.example.weather.common.usecases.models.WeatherTime
import com.example.weather.common.fragments.BindingFragment
import com.example.weather.days.dialogsearch.DialogSearchFragment
import com.example.weather.days.dialogsearch.DialogSearchFragment.Companion.REQUEST_KEY
import com.example.weather.days.dialogsearch.DialogSearchFragment.Companion.TOWN_KEY
import com.example.weather.daytime.DayTimeWeatherFragment.Companion.DAY_POS_KEY
import com.example.weather.daytime.DayTimeWeatherFragment.Companion.DAY_TOWN_KEY
import com.mikepenz.fastadapter.adapters.FastItemAdapter

private const val APP_BAR_LAYOUT_EXPANDED_KEY = "app.bar.layout.is.expanded.key"

class DaysWeatherFragment : BindingFragment<FragmentDaysWeatherBinding>(
    FragmentDaysWeatherBinding::inflate
) {
    private val viewModel: DaysWeatherViewModel by viewModels { getViewModelFactory() }
    private var appBarLayoutIsExpanded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.updateDaysWeatherState(TOWN_ID)
        } else {
            appBarLayoutIsExpanded =
                savedInstanceState.getBoolean(APP_BAR_LAYOUT_EXPANDED_KEY, appBarLayoutIsExpanded)
        }
    }

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
        childFragmentManager.clearFragmentResultListener(REQUEST_KEY)
        super.onDestroyView()
    }

    companion object {
        const val TOWN_ID = "Тамбов"
    }
}