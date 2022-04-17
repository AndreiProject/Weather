package com.example.weather.common.network.source

import com.example.weather.common.network.repository.Service
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private const val BASE_URL = "https://api.openweathermap.org"
    private var retrofit: Retrofit? = null

    fun getService(): Service {
        if (retrofit == null) {
            val builder = OkHttpClient().newBuilder()
            retrofit = Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(Service::class.java)
    }
}