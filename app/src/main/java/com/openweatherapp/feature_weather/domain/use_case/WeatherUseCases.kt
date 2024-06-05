package com.openweatherapp.feature_weather.domain.use_case

import com.openweatherapp.feature_weather.domain.use_case.GetCurrentWeather

data class WeatherUseCases(
    val getCurrentWeather: GetCurrentWeather,
    val getWeatherHistory: GetWeatherHistory
)