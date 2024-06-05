package com.openweatherapp.feature_weather.data.repository

import com.openweatherapp.common.local.AppDatabase
import com.openweatherapp.feature_weather.data.local.model.WeatherEntity
import com.openweatherapp.common.network.OpenWeatherApi
import com.openweatherapp.feature_weather.data.remote.model.CurrentWeatherDto
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.feature_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val db: AppDatabase
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String,
        units: String,
        apiKey: String
    ): CurrentWeatherDto {
        return api.getCurrentWeather(
            latitude = latitude,
            longitude = longitude,
            units = units,
            apiKey = apiKey
        )
    }

    override suspend fun saveWeather(weatherEntity: WeatherEntity) {
        db.getWeatherHistoryDao().saveWeather(weatherEntity)
    }

    override fun getWeatherHistory(uID: String?): Flow<List<CurrentWeather>> {
        return db.getWeatherHistoryDao().getWeatherHistory(uID).map {
            it.map {
                it.toCurrentWeather()
            }
        }
    }
}