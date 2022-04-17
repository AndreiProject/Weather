package com.example.weather.common.utils

import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.*
import com.example.weather.common.appComponent
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun Fragment.showMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.getNavController() = Navigation.findNavController(requireView())

fun Fragment.getViewModelFactory() = requireActivity().appComponent.factory

fun <T : GenericItem> RecyclerView.getFastAdapter(adapterItemType: Class<T>) =
    adapter as FastItemAdapter<T>

fun <T : GenericItem> FastItemAdapter<T>.setData(listItemAdapter: List<T>) {
    FastAdapterDiffUtil[itemAdapter] = listItemAdapter
}

fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    observer: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect { value -> observer(value) }
        }
    }
}

fun ImageView.loadByUrl(imgUrl: String) {
    if (imgUrl.isNotBlank()) {
        Picasso.get().load(imgUrl).error(R.drawable.ic_cloud).into(this)
    }
}