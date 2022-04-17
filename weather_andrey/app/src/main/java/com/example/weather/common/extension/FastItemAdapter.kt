package com.example.weather.common.extension

import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

fun <T : GenericItem> FastItemAdapter<T>.setItems(items: List<T>) {
    FastAdapterDiffUtil[itemAdapter] = items
}