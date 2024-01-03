package com.tta.leanworx.weather.data

import com.tta.leanworx.weather.data.local.OpenWeatherDao
import com.tta.leanworx.weather.data.remote.OpenWeatherApi
import com.tta.leanworx.weather.data.remote.dto.CurrentAndForecastsWeatherDataResponse
import com.tta.leanworx.weather.data.repository.LocationRepositoryImpl
import com.tta.leanworx.weather.domain.repository.LocationRepository
import com.tta.leanworx.weather.domain.utils.Response
import kotlinx.coroutines.test.runTest
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class LocationRepositoryTest {

    private lateinit var repository: LocationRepository
    private val openWeatherApi = mockk<OpenWeatherApi>()
    private val openWeatherDao = mockk<OpenWeatherDao>()

    @Before
    fun setup() {
        repository = LocationRepositoryImpl(openWeatherApi, openWeatherDao)
    }

    @Test
    fun `when getCurrentAndForecastsWeatherData is called, it should emit loading state and then success state`() =
        runTest {
            coEvery {
                openWeatherApi.getCurrentAndForecastsWeatherData(
                    lat = "41.9961", lon = "21.4316"
                )
            } returns sampleCurrentAndForecastsWeatherDataResponse

            val results = mutableListOf<Response<CurrentAndForecastsWeatherDataResponse>>()
            repository.getCurrentAndForecastsWeatherData("41.9961", "21.4316").collect { result ->
                results.add(result)
            }
            TestCase.assertEquals(
                Response.Loading<CurrentAndForecastsWeatherDataResponse>().data,
                results[0].data
            )

            TestCase.assertEquals(
                Response.Success(sampleCurrentAndForecastsWeatherDataResponse).data,
                results[1].data
            )
        }


}