package com.tta.leanworx.weather.data.local

import androidx.room.*
import com.tta.leanworx.weather.domain.models.UserLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface OpenWeatherDao {

    @Query("SELECT * FROM USER_SAVED_PLACES")
    fun getAllPlaces():Flow<List<UserLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place:UserLocation)

    @Delete
    suspend fun deletePlace(place:UserLocation)

}