package com.tta.leanworx.weather.presentation.states

import com.tta.leanworx.weather.data.remote.dto.CurrentAndForecastsWeatherDataResponse
import com.tta.leanworx.weather.domain.models.UserLocation

data class UIState(
    val currentWeather: CurrentWeather = CurrentWeather(),
    val isLoading:Boolean = true,
    val error:String = ""
)

data class CurrentWeather(
    val currentAndForecastsWeatherDataResponse: CurrentAndForecastsWeatherDataResponse = CurrentAndForecastsWeatherDataResponse(null,null),
    val userLocation: UserLocation = UserLocation()
)
