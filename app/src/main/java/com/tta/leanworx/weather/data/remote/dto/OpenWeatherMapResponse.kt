package com.tta.leanworx.weather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenWeatherMapResponse(
    @SerializedName("country")
    val country: String?,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("local_names")
    val localNames: LocalNames?=null,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String?,
    @SerializedName("state")
    val state: String?
)

data class LocalNames(
    val ar: String?,
    val de: String?,
    val en: String?,
    val es: String?,
    val fr: String?,
    val he: String?,
    val hi: String?,
    val ja: String?,
    val kn: String?,
    val ta: String?,
    val te: String?,
    val ur: String?
)