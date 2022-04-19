package com.example.weather.common.network.repository

import com.example.weather.common.network.repository.models.WeatherModel
import retrofit2.Response
import retrofit2.http.*

interface Service {
    @GET
    suspend fun getWeather(
        @Url requestUrl: String,
        @Query("q") townId: String,
        @Query("appid") keyId: String,
        @Query("cnt") cnt: Int,
        @Query("lang") lang: String,
        @Query("units") units: String,
    ): Response<WeatherModel>
}