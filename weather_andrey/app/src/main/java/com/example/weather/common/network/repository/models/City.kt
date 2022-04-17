package com.example.weather.common.network.repository.models

data class City(
    val id: Long,
    val name: String,
    val country: String,
    val timezone: Long,
)