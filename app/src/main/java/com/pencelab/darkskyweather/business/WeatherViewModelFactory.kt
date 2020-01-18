package com.pencelab.darkskyweather.business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pencelab.darkskyweather.repository.WeatherRepository

class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = WeatherViewModel(repository) as T

}