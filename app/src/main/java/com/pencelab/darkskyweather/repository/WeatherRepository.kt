package com.pencelab.darkskyweather.repository

import com.pencelab.darkskyweather.repository.source.network.NetworkService
import com.pencelab.darkskyweather.repository.model.WeatherResponse
import com.pencelab.darkskyweather.utils.DefaultDispatcherProvider
import com.pencelab.darkskyweather.utils.DispatcherProvider

class WeatherRepository constructor(
    private val weatherService: NetworkService,
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
) {

    companion object {

        @Volatile private var INSTANCE: WeatherRepository? = null

        fun getInstance(weatherService: NetworkService) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: WeatherRepository(weatherService).also { INSTANCE = it }
            }
    }

    suspend fun fetchCurrentWeather(
        key: String,
        latitude: String,
        longitude: String
    ): WeatherResponse = this.weatherService.getCurrentWeather(key, latitude, longitude)

}