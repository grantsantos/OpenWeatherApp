package com.openweatherapp.feature_weather.domain.repository

import com.openweatherapp.feature_weather.data.local.model.WeatherEntity
import com.openweatherapp.feature_weather.data.remote.model.CurrentWeatherDto
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        units: String,
        apiKey: String
    ) : CurrentWeatherDto

    suspend fun saveWeather(weatherEntity: WeatherEntity)
    fun getWeatherHistory(id: String?) : Flow<List<CurrentWeather>>
}