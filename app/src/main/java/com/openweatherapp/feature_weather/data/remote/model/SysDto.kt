package com.openweatherapp.feature_weather.data.remote.model


import com.google.gson.annotations.SerializedName

data class SysDto(
    @SerializedName("type")
    val type: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("sunrise")
    val sunrise: Long?,
    @SerializedName("sunset")
    val sunset: Long?
)