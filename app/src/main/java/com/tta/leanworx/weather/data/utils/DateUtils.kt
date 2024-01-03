package com.tta.leanworx.weather.data.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun dateTimeFromUnixUtc(time: Int, format: String = "K:mm a"): String {
    return try {
        val sdf = SimpleDateFormat(format)
        val netDate = Date(time.toLong() * 1000)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}