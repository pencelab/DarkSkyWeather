package com.pencelab.darkskyweather.business

import android.content.Context

interface ViewModelFactoryProvider {
    fun provideWeatherViewModelFactory(context: Context): WeatherViewModelFactory
}