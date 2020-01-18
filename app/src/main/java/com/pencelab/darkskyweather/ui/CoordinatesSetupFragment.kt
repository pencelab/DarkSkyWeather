package com.pencelab.darkskyweather.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.pencelab.darkskyweather.R
import com.pencelab.darkskyweather.business.WeatherViewModel
import com.pencelab.darkskyweather.utils.Injector

class CoordinatesSetupFragment : Fragment() {

    private lateinit var latitudeInput: EditText
    private lateinit var longitudeInput: EditText
    private lateinit var goButton: Button

    private val weatherViewModel: WeatherViewModel by viewModels({requireActivity()}) {
        Injector.provideWeatherViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_coordinates_setup, container, false)

        latitudeInput = view.findViewById(R.id.editText_latitude)
        longitudeInput = view.findViewById(R.id.editText_longitude)
        goButton = view.findViewById(R.id.button_go)

        latitudeInput.setText("37.8267")
        longitudeInput.setText("-122.4233")

        goButton.setOnClickListener {
            weatherViewModel.loadCurrentWeather(latitudeInput.text.toString(), longitudeInput.text.toString())
        }

        return view
    }


}
