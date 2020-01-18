package com.pencelab.darkskyweather.utils

import android.content.Context
import com.pencelab.darkskyweather.business.ViewModelFactoryProvider
import com.pencelab.darkskyweather.business.WeatherViewModelFactory
import com.pencelab.darkskyweather.repository.WeatherRepository
import com.pencelab.darkskyweather.repository.source.network.NetworkService

val Injector: ViewModelFactoryProvider
    get() = currentInjector

private object DefaultViewModelProvider: ViewModelFactoryProvider {

    private fun weatherService() = NetworkService()

    override fun provideWeatherViewModelFactory(context: Context): WeatherViewModelFactory {
        val repository = WeatherRepository.getInstance(this.weatherService())
        return WeatherViewModelFactory(repository)
    }
}

@Volatile private var currentInjector: ViewModelFactoryProvider = DefaultViewModelProvider