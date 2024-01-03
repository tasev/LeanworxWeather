package com.tta.leanworx.weather.domain.repository

import android.location.Location
import com.tta.leanworx.weather.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface LocationClient {

     fun getLocation(): Flow<Response<Location>>

    class LocationException( message:String):Exception(message)
}