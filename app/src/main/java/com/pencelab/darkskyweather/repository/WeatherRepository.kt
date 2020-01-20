package com.pencelab.darkskyweather.repository

import com.pencelab.darkskyweather.repository.source.network.NetworkService
import com.pencelab.darkskyweather.repository.model.DarkSkyResponse

class WeatherRepository constructor(private val weatherService: NetworkService) {

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
    ): DarkSkyResponse = this.weatherService.getCurrentWeather(key, latitude, longitude)

}