package com.openweatherapp.feature_weather.data.remote.model


import com.google.gson.annotations.SerializedName

data class CloudsDto(
    @SerializedName("all")
    val all: Int?
)