package com.pencelab.darkskyweather.business

import android.util.Log
import androidx.lifecycle.*
import com.pencelab.darkskyweather.repository.WeatherRepository
import com.pencelab.darkskyweather.repository.model.WeatherResult
import com.pencelab.darkskyweather.repository.model.WeatherError
import com.pencelab.darkskyweather.repository.model.WeatherLoading
import com.pencelab.darkskyweather.repository.model.WeatherRequestState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherViewModel internal constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    companion object {
        private const val DARKSKY_API_KEY = "cbb0a2ec980ab36a2811d0e3d2776a02"
    }

    private val _weather = MutableLiveData<WeatherRequestState>()
    val weather: LiveData<WeatherRequestState>
        get() = _weather

    init {
        loadCurrentWeather(getCurrentLatitude(), getCurrentLongitude())
    }

    fun loadCurrentWeather(latitude: String, longitude: String) = viewModelScope.launch {
        _weather.value = WeatherLoading

        val result = try {
            val weatherResponse = weatherRepository.fetchCurrentWeather(DARKSKY_API_KEY, latitude, longitude)
            WeatherResult(
                weatherResponse.timezone,
                weatherResponse.latitude,
                weatherResponse.longitude,
                weatherResponse.currently.temperature.roundToInt(),
                weatherResponse.currently.summary,
                weatherResponse.currently.icon
            )
        } catch(e: Exception) {
            WeatherError(e.message)
        }

        _weather.value = result
    }

    private fun getCurrentLatitude() = "9.6302"
    private fun getCurrentLongitude() = "-84.2542"


    private fun log(message: String) {
        Log.d("WeatherViewModel", message)
    }

}

