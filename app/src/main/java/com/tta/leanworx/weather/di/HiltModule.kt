package com.tta.leanworx.weather.di

import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.tta.leanworx.weather.data.local.OpenWeatherDatabase
import com.tta.leanworx.weather.data.remote.OpenWeatherApi
import com.tta.leanworx.weather.data.repository.LocationRepositoryImpl
import com.tta.leanworx.weather.domain.repository.LocationClient
import com.tta.leanworx.weather.domain.repository.LocationRepository
import com.tta.leanworx.weather.domain.utils.DefaultLocationManager
import com.tta.leanworx.weather.domain.utils.OpenWeatherMapUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Singleton
    @Provides
    fun getDefaultLocationManager(@ApplicationContext context: Context): LocationClient {
        return DefaultLocationManager(
            context,
            LocationServices.getFusedLocationProviderClient(context)
        )
    }


    @Singleton
    @Provides
    fun provideOpenWeatherMapApi(): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl(OpenWeatherMapUtil.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): OpenWeatherDatabase =
        Room.databaseBuilder(
            context = context,
            klass = OpenWeatherDatabase::class.java,
            name = OpenWeatherDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun getLocationRepository(
        openWeatherApi: OpenWeatherApi,
        openWeatherDatabase: OpenWeatherDatabase
    ): LocationRepository = LocationRepositoryImpl(openWeatherApi, openWeatherDatabase.dao)


}