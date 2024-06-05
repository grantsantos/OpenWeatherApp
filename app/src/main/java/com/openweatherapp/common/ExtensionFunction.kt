package com.openweatherapp.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.formatUnixTimestampToTimeString(): String {
    val date = Date(this * 1000) // Convert to milliseconds
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault() // Set to default time zone
    return sdf.format(date)
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}