package com.tta.leanworx.weather.data.remote

import com.tta.leanworx.weather.data.remote.dto.CurrentAndForecastsWeatherDataResponse
import com.tta.leanworx.weather.data.remote.dto.OpenWeatherMapResponse
import com.tta.leanworx.weather.domain.utils.OpenWeatherMapUtil
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("/geo/1.0/reverse")
    suspend fun getNameFromCoordinates(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("limit") limit:Int=10,
        @Query("appid") apiKey:String = OpenWeatherMapUtil.API_KEY
    ):List<OpenWeatherMapResponse>

    @GET("/geo/1.0/direct")
    suspend fun getCoordinatesFromName(
        @Query("q") name:String,
        @Query("limit") limit:Int=10,
        @Query("appid") apiKey:String = OpenWeatherMapUtil.API_KEY
    ):List<OpenWeatherMapResponse>

    @GET("/data/2.5/weather")
    suspend fun getCurrentAndForecastsWeatherData(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey:String = OpenWeatherMapUtil.API_KEY
    ): CurrentAndForecastsWeatherDataResponse


}