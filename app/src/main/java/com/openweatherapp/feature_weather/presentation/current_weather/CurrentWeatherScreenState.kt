package com.openweatherapp.feature_weather.presentation.current_weather

import com.google.firebase.auth.FirebaseUser
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather

data class CurrentWeatherScreenState(
    val currentWeatherData: CurrentWeather? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val isPermissionDialogVisible: Boolean = false,
    val weatherHistoryList: List<CurrentWeather> = listOf(),
    val isLogoutDialogVisible: Boolean = false,
    val isLoadingDialogVisible: Boolean = false
)