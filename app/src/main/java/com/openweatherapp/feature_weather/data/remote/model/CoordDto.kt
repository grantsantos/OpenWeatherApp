package com.openweatherapp.feature_weather.data.remote.model


import com.google.gson.annotations.SerializedName

data class CoordDto(
    @SerializedName("lon")
    val lon: Double?,
    @SerializedName("lat")
    val lat: Double?
)