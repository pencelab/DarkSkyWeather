package com.pencelab.darkskyweather.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.pencelab.darkskyweather.R
import com.pencelab.darkskyweather.business.WeatherViewModel
import com.pencelab.darkskyweather.repository.model.WeatherResult
import com.pencelab.darkskyweather.repository.model.WeatherError
import com.pencelab.darkskyweather.repository.model.WeatherLoading
import com.pencelab.darkskyweather.utils.Injector

class WeatherDataFragment : Fragment() {

    private lateinit var mainInfoLayout: ConstraintLayout
    private lateinit var location: TextView
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView
    private lateinit var icon: ImageView
    private lateinit var summary: TextView
    private lateinit var temperature: TextView
    private lateinit var spinner: ProgressBar
    private lateinit var errorIcon: ImageView
    private lateinit var error: TextView

    private val weatherViewModel: WeatherViewModel by viewModels({requireActivity()}) {
        Injector.provideWeatherViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_weather_data, container, false)

        this.mainInfoLayout = view.findViewById(R.id.layout_main_info)
        this.location = view.findViewById(R.id.textView_data_location)
        this.latitude = view.findViewById(R.id.textView_data_latitude)
        this.longitude = view.findViewById(R.id.textView_data_longitude)
        this.icon = view.findViewById(R.id.imageView_data_icon)
        this.summary = view.findViewById(R.id.textView_data_summary)
        this.temperature = view.findViewById(R.id.textView_data_temperature)
        this.spinner = view.findViewById(R.id.progressBar_data_spinner)
        this.errorIcon = view.findViewById(R.id.imageView_error_icon)
        this.error = view.findViewById(R.id.textView_error_message)

        this.subscribeUi()

        return view
    }

    private fun subscribeUi() {
        weatherViewModel.weather.observe(viewLifecycleOwner) { weather ->
            when(weather) {
                is WeatherResult -> setResponseReceivedState(weather)
                is WeatherError -> setErrorState(weather)
                is WeatherLoading -> setLoadingState()
            }
        }
    }

    private fun setResponseReceivedState(weather: WeatherResult) {
        location.text = weather.location
        latitude.text = getString(R.string.data_latitude, weather.latitude.toString())
        longitude.text = getString(R.string.data_longitude, weather.longitude.toString())
        temperature.text = getString(R.string.data_degrees, weather.temperature)
        summary.text = weather.summary

        try {
            val id = context!!.resources.getIdentifier(weather.icon.replace('-', '_'), "string", context!!.packageName)
            log(getString(id))
        } catch (e: Exception) {
            log(getString(R.string.def))
        }

        this.hideLoadingStateWidgets()
        this.hideErrorStateWidgets()
        this.showResponseReceivedWidgets()
    }

    private fun setErrorState(weather: WeatherError) {
        if(weather.message != null)
            this.error.text = weather.message

        this.hideLoadingStateWidgets()
        this.hideResponseReceivedWidgets()
        this.showErrorStateWidgets()
    }

    private fun setLoadingState() {
        this.hideErrorStateWidgets()
        this.hideResponseReceivedWidgets()
        this.showLoadingStateWidgets()
    }

    private fun showResponseReceivedWidgets() {
        location.visibility = View.VISIBLE
        latitude.visibility = View.VISIBLE
        longitude.visibility = View.VISIBLE
        mainInfoLayout.visibility = View.VISIBLE
    }

    private fun hideResponseReceivedWidgets() {
        location.visibility = View.GONE
        latitude.visibility = View.GONE
        longitude.visibility = View.GONE
        mainInfoLayout.visibility = View.GONE
    }

    private fun showLoadingStateWidgets() {
        this.spinner.visibility = View.VISIBLE
    }

    private fun hideLoadingStateWidgets() {
        this.spinner.visibility = View.GONE
    }

    private fun showErrorStateWidgets() {
        this.errorIcon.visibility = View.VISIBLE
        this.error.visibility = View.VISIBLE
    }

    private fun hideErrorStateWidgets() {
        this.errorIcon.visibility = View.GONE
        this.error.visibility = View.GONE
    }

    private fun log(message: String) {
        Log.d("WeatherDataFragment", message)
    }

}
