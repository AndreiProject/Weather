package com.example.weather.common.extension

import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter

fun <T : GenericItem> RecyclerView.getFastAdapter() = adapter as FastItemAdapter<T>