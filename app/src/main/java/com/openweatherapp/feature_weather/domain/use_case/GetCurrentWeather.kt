package com.openweatherapp.feature_weather.domain.use_case

import com.openweatherapp.common.Resource
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.feature_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrentWeather(
    private val repository: WeatherRepository
) {
    fun execute(
        latitude: String,
        longitude: String,
        units: String,
        apiKey: String,
        firebaseUid: String?
    ) : Flow<Resource<CurrentWeather>> = flow {

        try {
            emit(Resource.Loading())

            val currentWeatherData = repository.getCurrentWeather(
                latitude = latitude,
                longitude = longitude,
                units = units,
                apiKey = apiKey
            ).toCurrentWeather()

            val weatherEntity = currentWeatherData.toWeatherEntity(firebaseUid)
            repository.saveWeather(weatherEntity)

            emit(Resource.Success(currentWeatherData))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something went wrong"))
        }
    }
}