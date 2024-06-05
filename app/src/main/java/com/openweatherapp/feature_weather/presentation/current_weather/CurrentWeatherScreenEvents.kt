package com.openweatherapp.feature_weather.presentation.current_weather

sealed class CurrentWeatherScreenEvents {
    data class GetCurrentWeather(
        val lat: String,
        val lon: String,
        val units: String,
        val apiKey: String
    ) : CurrentWeatherScreenEvents()

    object ShowPermissionDialog : CurrentWeatherScreenEvents()
    object HidePermissionDialog : CurrentWeatherScreenEvents()
    object FetchWeatherHistory: CurrentWeatherScreenEvents()
    object ShowLogoutDialog: CurrentWeatherScreenEvents()
    object HideLogoutDialog: CurrentWeatherScreenEvents()
    object Logout: CurrentWeatherScreenEvents()
}