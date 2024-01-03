package com.tta.leanworx.weather.presentation.states

import com.tta.leanworx.weather.domain.models.UserLocation

data class LocationState(
    val location: UserLocation = UserLocation(),
    val isLoading:Boolean = true,
    val error:String = ""
)