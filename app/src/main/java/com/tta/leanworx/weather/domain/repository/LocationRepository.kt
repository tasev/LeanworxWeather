package com.tta.leanworx.weather.domain.repository

import com.tta.leanworx.weather.data.remote.dto.CurrentAndForecastsWeatherDataResponse
import com.tta.leanworx.weather.domain.models.UserLocation
import com.tta.leanworx.weather.data.remote.dto.OpenWeatherMapResponse
import com.tta.leanworx.weather.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getNameFromCoordinates(
        latitude: String,
        longitude: String
    ): Flow<Response<OpenWeatherMapResponse>>

    suspend fun getCoordinatesFromName(name: String): Flow<Response<List<UserLocation>>>

    suspend fun getCurrentAndForecastsWeatherData(
        latitude: String,
        longitude: String
    ): Flow<Response<CurrentAndForecastsWeatherDataResponse>>

    fun getAllPlaces():Flow<List<UserLocation>>

    suspend fun insertPlace(place:UserLocation)

    suspend fun deletePlace(place: UserLocation)
}