package com.pencelab.darkskyweather.business

import androidx.lifecycle.*
import com.pencelab.darkskyweather.repository.WeatherRepository
import com.pencelab.darkskyweather.repository.model.*
import com.pencelab.darkskyweather.utils.DefaultDispatcherProvider
import com.pencelab.darkskyweather.utils.DispatcherProvider
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class WeatherViewModel internal constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()
): ViewModel() {

    companion object {
        private const val DARKSKY_API_KEY = "cbb0a2ec980ab36a2811d0e3d2776a02"
    }

    private val _weather = MutableLiveData<WeatherRequestState>()
    val weather: LiveData<WeatherRequestState>
        get() = _weather

    fun loadCurrentWeather(latitude: String, longitude: String) = viewModelScope.launch(this.dispatchers.main()) {
        _weather.value = WeatherLoading

        val result = try {
            val weatherResponse = weatherRepository.fetchCurrentWeather(DARKSKY_API_KEY, latitude, longitude)
            WeatherResult(
                weatherResponse.timezone.split('/').last().replace('_',' '),
                weatherResponse.latitude,
                weatherResponse.longitude,
                weatherResponse.currently?.temperature?.roundToInt()?.toString() ?: "?",
                weatherResponse.currently?.summary ?: "",
                weatherResponse.currently?.icon ?: "def"
            )
        } catch(e: Exception) {
            WeatherError(e.message)
        }

        _weather.value = result
    }

}

