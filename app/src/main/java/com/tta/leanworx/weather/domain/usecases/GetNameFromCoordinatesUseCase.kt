package com.tta.leanworx.weather.domain.usecases

import com.tta.leanworx.weather.data.remote.dto.OpenWeatherMapResponse
import com.tta.leanworx.weather.domain.repository.LocationRepository
import com.tta.leanworx.weather.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNameFromCoordinatesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(
        latitude: String,
        longitude: String
    ): Flow<Response<OpenWeatherMapResponse>> {
        return locationRepository.getNameFromCoordinates(latitude, longitude)
    }
}