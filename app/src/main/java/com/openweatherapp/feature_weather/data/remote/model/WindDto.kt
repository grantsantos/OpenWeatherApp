package com.openweatherapp.feature_weather.data.remote.model


import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("speed")
    val speed: Double?,
    @SerializedName("deg")
    val deg: Int?,
    @SerializedName("gust")
    val gust: Double?
)