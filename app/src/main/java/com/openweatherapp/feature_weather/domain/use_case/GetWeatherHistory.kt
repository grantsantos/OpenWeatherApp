package com.openweatherapp.feature_weather.domain.use_case

import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.feature_weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherHistory(
    private val repository: WeatherRepository
) {
    fun execute(uID: String?) : Flow<List<CurrentWeather>> {
        return repository.getWeatherHistory(uID)
    }
}