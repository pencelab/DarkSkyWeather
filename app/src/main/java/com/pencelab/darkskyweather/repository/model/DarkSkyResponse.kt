package com.pencelab.darkskyweather.repository.model

data class DarkSkyResponse(
    val timezone: String,
    val latitude: Double,
    val longitude: Double,
    val currently: CurrentlyNode?
) {
    data class CurrentlyNode(
        val apparentTemperature: Double?,
        val cloudCover: Double?,
        val dewPoint: Double?,
        val humidity: Double?,
        val icon: String?,
        val nearestStormBearing: Double?,
        val nearestStormDistance: Double?,
        val ozone: Double?,
        val precipAccumulation: Double?,
        val precipIntensity: Double?,
        val precipIntensityError: Double?,
        val precipProbability: Double?,
        val precipType: String?,
        val pressure: Double?,
        val summary: String?,
        val temperature: Double?,
        val time: Long,
        val uvIndex: Int?,
        val visibility: Double?,
        val windBearing: Double?,
        val windGust: Double?,
        val windSpeed: Double?
    )
}