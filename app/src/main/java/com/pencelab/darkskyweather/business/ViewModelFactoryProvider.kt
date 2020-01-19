package com.pencelab.darkskyweather.business

interface ViewModelFactoryProvider {
    fun provideWeatherViewModelFactory(): WeatherViewModelFactory
}