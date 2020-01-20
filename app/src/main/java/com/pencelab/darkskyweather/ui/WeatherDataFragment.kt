package com.pencelab.darkskyweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.pencelab.darkskyweather.business.WeatherViewModel
import com.pencelab.darkskyweather.databinding.FragmentWeatherDataBinding
import com.pencelab.darkskyweather.repository.model.WeatherResult
import com.pencelab.darkskyweather.repository.model.WeatherError
import com.pencelab.darkskyweather.repository.model.WeatherLoading
import com.pencelab.darkskyweather.utils.Injector

class WeatherDataFragment : Fragment() {

    private lateinit var binding: FragmentWeatherDataBinding

    private val weatherViewModel: WeatherViewModel by viewModels({requireActivity()}) {
        Injector.provideWeatherViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDataBinding.inflate(inflater, container, false)
        context ?: return binding.root

        this.subscribeUi()

        return binding.root
    }

    private fun subscribeUi() {
        weatherViewModel.weather.observe(viewLifecycleOwner) { weatherRequestState ->
            binding.constraintLayoutData.visibility = View.VISIBLE
            when(weatherRequestState) {
                is WeatherResult -> {
                    binding.weatherResult = weatherRequestState
                    setResponseReceivedState()
                }
                is WeatherError -> {
                    binding.weatherError = weatherRequestState
                    setErrorState()
                }
                is WeatherLoading -> setLoadingState()
            }
        }
    }

    private fun setResponseReceivedState() {
        this.hideLoadingStateWidgets()
        this.hideErrorStateWidgets()
        this.showResponseReceivedWidgets()
    }

    private fun setErrorState() {
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
        this.binding.textViewDataLocation.visibility = View.VISIBLE
        this.binding.textViewDataLatitude.visibility = View.VISIBLE
        this.binding.textViewDataLongitude.visibility = View.VISIBLE
        this.binding.layoutMainInfo.visibility = View.VISIBLE
    }

    private fun hideResponseReceivedWidgets() {
        this.binding.textViewDataLocation.visibility = View.GONE
        this.binding.textViewDataLatitude.visibility = View.GONE
        this.binding.textViewDataLongitude.visibility = View.GONE
        this.binding.layoutMainInfo.visibility = View.GONE
    }

    private fun showLoadingStateWidgets() {
        this.binding.progressBarDataSpinner.visibility = View.VISIBLE
    }

    private fun hideLoadingStateWidgets() {
        this.binding.progressBarDataSpinner.visibility = View.GONE
    }

    private fun showErrorStateWidgets() {
        this.binding.imageViewErrorIcon.visibility = View.VISIBLE
        this.binding.textViewErrorMessage.visibility = View.VISIBLE
    }

    private fun hideErrorStateWidgets() {
        this.binding.imageViewErrorIcon.visibility = View.GONE
        this.binding.textViewErrorMessage.visibility = View.GONE
    }

}
