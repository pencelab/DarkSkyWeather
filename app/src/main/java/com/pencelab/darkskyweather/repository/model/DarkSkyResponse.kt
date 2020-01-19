package com.pencelab.darkskyweather.repository.model

data class DarkSkyResponse(
    val timezone: String,
    val latitude: Double,
    val longitude: Double,
    val currently: CurrentlyNode
) {
    data class CurrentlyNode(
        val time: Long,
        val summary: String,
        val icon: String,
        val temperature: Double
    )
}