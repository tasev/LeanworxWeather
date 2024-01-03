package com.tta.leanworx.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrentAndForecastsWeatherDataResponse(
    @SerializedName("coord")
    val coordinates: CoordinatesWeatherData? = null,
    @SerializedName("main")
    val main: CurrentWeatherData? = null,
    @SerializedName("sys")
    val sys: SysWeatherdata? = null,
    @SerializedName("weather")
    val weather: ArrayList<WeatherWeatherdata?> = arrayListOf(),
    @SerializedName("name")
    val name: String? = null

)

data class CoordinatesWeatherData(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0,
)

data class CurrentWeatherData(
    @SerializedName("temp")
    val temp: String?,
    @SerializedName("feels_like")
    val feelsLike: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("humidity")
    val humidity: String
)

data class SysWeatherdata(
    @SerializedName("sunrise")
    val sunrise: String?,
    @SerializedName("sunset")
    val sunset: String,
)

data class WeatherWeatherdata(
    @SerializedName("main")
    val main: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)