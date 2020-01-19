package com.pencelab.darkskyweather.repository.model

sealed class WeatherRequestState

data class WeatherResult(
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: String,
    val summary: String,
    val icon: String
) : WeatherRequestState()

data class WeatherError(val message: String?) : WeatherRequestState()

object WeatherLoading : WeatherRequestState()