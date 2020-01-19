package com.pencelab.darkskyweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.pencelab.darkskyweather.R
import com.pencelab.darkskyweather.business.WeatherViewModel
import com.pencelab.darkskyweather.databinding.FragmentCoordinatesSetupBinding
import com.pencelab.darkskyweather.utils.Injector

class CoordinatesSetupFragment : Fragment() {

    private lateinit var binding: FragmentCoordinatesSetupBinding

    private val weatherViewModel: WeatherViewModel by viewModels({requireActivity()}) {
        Injector.provideWeatherViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCoordinatesSetupBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.fragment_autocomplete) as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                val latitude: String = place.latLng?.latitude.toString()
                val longitude: String = place.latLng?.longitude.toString()
                binding.editTextLatitude.setText(latitude)
                binding.editTextLongitude.setText(longitude)
                weatherViewModel.loadCurrentWeather(latitude, longitude)
            }

            override fun onError(status: Status) {
                if(status != Status.RESULT_CANCELED)
                    Toast.makeText(context, getString(R.string.autocomplete_error_message), Toast.LENGTH_LONG).show()
            }

        })

        binding.editTextLatitude.setText(getString(R.string.initial_latitude))
        binding.editTextLongitude.setText(getString(R.string.initial_longitude))

        binding.buttonGo.setOnClickListener {
            loadCurrentWeather()
        }

        loadCurrentWeather()

        return binding.root
    }

    private fun loadCurrentWeather() {
        weatherViewModel.loadCurrentWeather(binding.editTextLatitude.text.toString(), binding.editTextLongitude.text.toString())
    }

}
