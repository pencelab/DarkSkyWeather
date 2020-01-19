package com.pencelab.darkskyweather.repository.model

sealed class WeatherRequestState

data class WeatherResult(
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Int,
    val summary: String,
    val icon: String = "def"
) : WeatherRequestState()

data class WeatherError(val message: String?) : WeatherRequestState()

object WeatherLoading : WeatherRequestState()