package com.openweatherapp.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object SplashScreen: Screen()
    @Serializable
    data object LoginScreen: Screen()
    @Serializable
    data object SignUpScreen: Screen()
    @Serializable
    data object WeatherScreen: Screen()
}
