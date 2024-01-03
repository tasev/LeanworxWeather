package com.tta.leanworx.weather.data

import com.tta.leanworx.weather.data.remote.dto.CoordinatesWeatherData
import com.tta.leanworx.weather.data.remote.dto.CurrentAndForecastsWeatherDataResponse
import com.tta.leanworx.weather.data.remote.dto.CurrentWeatherData
import com.tta.leanworx.weather.data.remote.dto.SysWeatherdata
import com.tta.leanworx.weather.data.remote.dto.WeatherWeatherdata


val sampleCurrentAndForecastsWeatherDataResponse: CurrentAndForecastsWeatherDataResponse
    get() = CurrentAndForecastsWeatherDataResponse(
        coordinates = CoordinatesWeatherData(
            lat = 41.9961,
            lon = 21.4316,
        ),
        main = CurrentWeatherData(
            temp = "-0.1",
            feelsLike = "-0.1",
            pressure = "1016",
            humidity = "100"
        ),
        sys = SysWeatherdata(
            sunrise = "1704175339",
            sunset = "1704208398"
        ),
        weather = arrayListOf(
            WeatherWeatherdata(
                main = "Fog",
                description = "fog",
                icon = "50n"
            )
        ),
        name = "Skopje"
    )
