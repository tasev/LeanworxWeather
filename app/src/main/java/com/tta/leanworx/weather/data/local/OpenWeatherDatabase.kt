package com.tta.leanworx.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tta.leanworx.weather.domain.models.UserLocation


@Database(entities = [UserLocation::class], version = 1, exportSchema = false)
abstract class OpenWeatherDatabase : RoomDatabase() {
    abstract val dao: OpenWeatherDao

    companion object {
        const val DATABASE_NAME = "USER_SAVED_PLACES"
    }
}