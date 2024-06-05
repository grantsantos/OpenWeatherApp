package com.openweatherapp.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object LoginScreen: Screen()
    object SignUpScreen: Screen()
    object WeatherScreen: Screen()
}

object AppRouter {
    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
}