package com.pencelab.darkskyweather.business

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pencelab.darkskyweather.repository.WeatherRepository
import com.pencelab.darkskyweather.repository.model.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherViewModel internal constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    companion object {
        private const val DARKSKY_API_KEY = "cbb0a2ec980ab36a2811d0e3d2776a02"
    }

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather>
        get() = _weather

    init {
        loadCurrentWeather(getCurrentLatitude(), getCurrentLongitude())
    }

    fun loadCurrentWeather(latitude: String, longitude: String) = viewModelScope.launch {
        _spinner.value = true
        val weatherResponse = weatherRepository.fetchCurrentWeather(DARKSKY_API_KEY, latitude, longitude)
        val weather = Weather(
            weatherResponse.timezone,
            weatherResponse.latitude,
            weatherResponse.longitude,
            weatherResponse.currently.temperature.roundToInt(),
            weatherResponse.currently.summary,
            getIconUrl(weatherResponse.currently.icon)
        )
        delay(5000)
        _spinner.value = false
        _weather.value = weather
    }

    private fun getIconUrl(icon: String): String {
        return "GOOD"
    }

    private fun getCurrentLatitude() = "9.6302"
    private fun getCurrentLongitude() = "-84.2542"


    private fun log(message: String) {
        Log.d("WeatherViewModel", message)
    }

}

