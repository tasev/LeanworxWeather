package com.tta.leanworx.weather.data.repository

import com.tta.leanworx.weather.data.local.OpenWeatherDao
import com.tta.leanworx.weather.data.remote.OpenWeatherApi
import com.tta.leanworx.weather.data.remote.dto.CurrentAndForecastsWeatherDataResponse
import com.tta.leanworx.weather.data.remote.dto.OpenWeatherMapResponse
import com.tta.leanworx.weather.domain.models.UserLocation
import com.tta.leanworx.weather.domain.repository.LocationRepository
import com.tta.leanworx.weather.domain.utils.Response
import com.tta.leanworx.weather.domain.utils.roundOfToFour
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class LocationRepositoryImpl(
    private val openWeatherApi: OpenWeatherApi,
    private val dao: OpenWeatherDao,
) : LocationRepository {

    override suspend fun getNameFromCoordinates(
        latitude: String,
        longitude: String
    ): Flow<Response<OpenWeatherMapResponse>> = flow {
        emit(Response.Loading())
        try {
            val response = openWeatherApi.getNameFromCoordinates(lat = latitude, lon = longitude)
            if (response.isEmpty()) {
                emit(Response.Error("Sorry can't fetch location"))
                return@flow
            }

            emit(Response.Success(response[0]))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server check your internet connection"))
        }
    }

    override suspend fun getCoordinatesFromName(name: String): Flow<Response<List<UserLocation>>> =
        flow {
            emit(Response.Loading())
            try {
                val response = openWeatherApi.getCoordinatesFromName(name = name).map {
                    UserLocation(
                        city = it.name ?: "",
                        state = it.state ?: "",
                        country = it.country ?: "",
                        lat = it.lat.roundOfToFour(),
                        long = it.lon.roundOfToFour()
                    )
                }
                if (response.isEmpty()) {
                    emit(Response.Error("Sorry no result found."))
                    return@flow
                }

                emit(Response.Success(response))
            } catch (e: HttpException) {
                emit(Response.Error(message = "Oops, something went wrong"))
            } catch (e: IOException) {
                emit(Response.Error(message = "Couldn't reach server check your internet connection"))
            }
        }

    override suspend fun getCurrentAndForecastsWeatherData(
        latitude: String,
        longitude: String
    ): Flow<Response<CurrentAndForecastsWeatherDataResponse>> = flow {
        emit(Response.Loading())
        try {
            val response = openWeatherApi.getCurrentAndForecastsWeatherData(lat = latitude, lon = longitude)
            if (response.main==null) {
                emit(Response.Error("Sorry can't fetch location"))
                return@flow
            }

            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "Couldn't reach server check your internet connection"))
        }
    }

    override fun getAllPlaces(): Flow<List<UserLocation>> {
        return dao.getAllPlaces()
    }

    override suspend fun insertPlace(place: UserLocation) {
        dao.insertPlace(place)
    }

    override suspend fun deletePlace(place: UserLocation) {
        dao.deletePlace(place)
    }
}