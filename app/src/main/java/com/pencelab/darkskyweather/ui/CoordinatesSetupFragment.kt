package com.pencelab.darkskyweather.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.pencelab.darkskyweather.R

class CoordinatesSetupFragment : Fragment() {

    private val lat = "37.8267"
    private val lon = "-122.4233"

    private lateinit var latitudeInput: EditText
    private lateinit var longitudeInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_coordinates_setup, container, false)

        latitudeInput = view.findViewById(R.id.editText_latitude)
        longitudeInput = view.findViewById(R.id.editText_longitude)

        latitudeInput.setText(lat)
        longitudeInput.setText(lon)

        return view
    }


}
