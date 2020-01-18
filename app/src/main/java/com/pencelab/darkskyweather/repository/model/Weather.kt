package com.pencelab.darkskyweather.repository.model

data class Weather(
    val location: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Int,
    val summary: String,
    val iconUrl: String = ""
)






