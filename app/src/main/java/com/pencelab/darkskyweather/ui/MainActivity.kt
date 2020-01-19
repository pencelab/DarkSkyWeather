package com.pencelab.darkskyweather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.pencelab.darkskyweather.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!Places.isInitialized())
            Places.initialize(applicationContext, "AIzaSyDPJ7QVSvSpDfigKuX6x5uiCq6AJdUVPQc")
    }

}
