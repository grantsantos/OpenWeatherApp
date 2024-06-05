package com.openweatherapp.common

object Constants {

    const val BASE_URL = "https://api.openweathermap.org/"

    const val API_KEY = "insert your api key here"

    const val LOCATION_NOTIFICATION_CHANNEL_ID = "location"

    fun getCompleteImageUrl(imageCode: String) : String {
        return "https://openweathermap.org/img/wn/$imageCode@4x.png"
    }

    const val ENCRYPTED_SHARED_PREF = "ENCRYPTED_SHARED_PREF"
    const val IV_PREF = "IV_PREF"
    const val ENCRYPTED_BYTE_PREF = "ENCRYPTED_BYTE_PREF"
}