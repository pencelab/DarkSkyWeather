package com.pencelab.darkskyweather.business

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import com.pencelab.darkskyweather.CoroutineTestRule
import com.pencelab.darkskyweather.any
import com.pencelab.darkskyweather.repository.WeatherRepository
import com.pencelab.darkskyweather.repository.model.DarkSkyResponse
import com.pencelab.darkskyweather.repository.model.WeatherError
import com.pencelab.darkskyweather.repository.model.WeatherLoading
import com.pencelab.darkskyweather.repository.model.WeatherResult
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalStateException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: WeatherRepository

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setUp() {
        this.weatherViewModel = WeatherViewModel(this.repository, coroutinesTestRule.testDispatcherProvider)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Calls repository fetchCurrentWeather function only once`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        weatherViewModel.loadCurrentWeather("", "")
        verify(repository).fetchCurrentWeather(any(), any(), any())
    }

    @Test
    fun `Updates LiveData with a WeatherLoading object when fetching current weather`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val darkSkyResponse = mock(DarkSkyResponse::class.java)
        `when`(repository.fetchCurrentWeather(anyString(), anyString(), anyString())).thenReturn(darkSkyResponse)

        val testObserver = weatherViewModel.weather
            .test()

        weatherViewModel.loadCurrentWeather("", "")

        val history = testObserver.valueHistory()
        assertTrue(history.contains(WeatherLoading))
    }

    @Test
    fun `Updates LiveData with a WeatherResult object when fetching current weather correctly`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val darkSkyResponse = mock(DarkSkyResponse::class.java)
        val currentlyNode = mock(DarkSkyResponse.CurrentlyNode::class.java)
        `when`(repository.fetchCurrentWeather(anyString(), anyString(), anyString())).thenReturn(darkSkyResponse)
        `when`(darkSkyResponse.timezone).thenReturn("location/Specific_Place")
        `when`(darkSkyResponse.latitude).thenReturn(45.5)
        `when`(darkSkyResponse.longitude).thenReturn(54.9)
        `when`(darkSkyResponse.currently).thenReturn(currentlyNode)
        `when`(currentlyNode.temperature).thenReturn(25.6)
        `when`(currentlyNode.summary).thenReturn("Clear Day")
        `when`(currentlyNode.icon).thenReturn("clear-day")

        val expected = WeatherResult(
            "Specific Place",
            45.5,
            54.9,
            "26",
            "Clear Day",
            "clear-day"
        )

        weatherViewModel.loadCurrentWeather("45.5", "54.9")

        weatherViewModel.weather
            .test()
            .assertValue{ it == expected }

    }

    @Test
    fun `Updates LiveData with a WeatherError object when fetching current weather throws an exception`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val errorMessage = "Error!!!"
        `when`(repository.fetchCurrentWeather(anyString(), anyString(), anyString())).thenThrow(IllegalStateException(errorMessage))

        val expected = WeatherError(errorMessage)

        weatherViewModel.loadCurrentWeather("abc", "54.9")

        weatherViewModel.weather
            .test()
            .assertValue{ it == expected }

    }
}