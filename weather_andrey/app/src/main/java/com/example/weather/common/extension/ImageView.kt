package com.example.weather.common.extension

import android.widget.ImageView
import com.example.weather.R
import com.squareup.picasso.Picasso

fun ImageView.loadByUrl(imgUrl: String) {
    if (imgUrl.isNotBlank()) {
        Picasso.get().load(imgUrl).error(R.drawable.ic_cloud).into(this)
    }
}