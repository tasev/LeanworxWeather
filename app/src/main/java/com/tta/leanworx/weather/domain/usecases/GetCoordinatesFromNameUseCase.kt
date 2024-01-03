package com.tta.leanworx.weather.domain.usecases

import com.tta.leanworx.weather.domain.models.UserLocation
import com.tta.leanworx.weather.domain.repository.LocationRepository
import com.tta.leanworx.weather.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoordinatesFromNameUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(name: String): Flow<Response<List<UserLocation>>> {
        return locationRepository.getCoordinatesFromName(name)
    }
}