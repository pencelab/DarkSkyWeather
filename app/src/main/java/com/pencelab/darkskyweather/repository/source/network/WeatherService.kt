package com.pencelab.darkskyweather.repository.source.network

import com.pencelab.darkskyweather.repository.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast/{key}/{latitude},{longitude}")
    suspend fun getCurrentWeather(
        @Path(value = "key", encoded = false) key: String,
        @Path(value = "latitude", encoded = false) latitude: String,
        @Path(value = "longitude", encoded = false) longitude: String,
        @Query("exclude") exclude: String = "minutely,hourly,daily,alerts,flags",
        @Query("units") units: String = "auto"
    ): WeatherResponse

}