package com.pencelab.darkskyweather.utils

import okhttp3.*

val HttpClient: OkHttpClient
    get() = currentHttpClient

@Volatile
private var currentHttpClient: OkHttpClient = DefaultHttpClient

private object DefaultHttpClient: OkHttpClient()
