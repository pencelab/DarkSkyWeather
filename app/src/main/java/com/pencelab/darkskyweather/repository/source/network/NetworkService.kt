package com.pencelab.darkskyweather.repository.source.network

import com.pencelab.darkskyweather.repository.model.DarkSkyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.darksky.net/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService = retrofit.create(DarkSkyService::class.java)

    suspend fun getCurrentWeather(key: String, latitude: String, longitude: String): DarkSkyResponse = withContext(Dispatchers.Default) {
        weatherService.getCurrentWeather(key, latitude, longitude)
    }

}