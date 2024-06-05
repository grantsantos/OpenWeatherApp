package com.openweatherapp.common.network

import com.openweatherapp.feature_weather.data.remote.model.CurrentWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ) : CurrentWeatherDto
}