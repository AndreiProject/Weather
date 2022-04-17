package com.example.weather.common.network.repository.models

import com.google.gson.annotations.SerializedName

data class Precipitation(
    @SerializedName("3h")
    val height: Double
)