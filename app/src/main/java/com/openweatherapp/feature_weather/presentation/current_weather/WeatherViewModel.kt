package com.openweatherapp.feature_weather.presentation.current_weather

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.openweatherapp.common.Resource
import com.openweatherapp.feature_weather.domain.use_case.WeatherUseCases
import com.openweatherapp.navigation.AppRouter
import com.openweatherapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val TAG = "WeatherViewModel"

    private val _state = mutableStateOf(CurrentWeatherScreenState())
    val state: State<CurrentWeatherScreenState> = _state

    fun onEvent(event: CurrentWeatherScreenEvents) {
        when (event) {
            is CurrentWeatherScreenEvents.GetCurrentWeather -> {
                getCurrentWeather(
                    lat = event.lat,
                    lon = event.lon,
                    units = event.units,
                    apiKey = event.apiKey
                )
            }

            CurrentWeatherScreenEvents.ShowPermissionDialog -> {
                _state.value = state.value.copy(
                    isPermissionDialogVisible = true
                )
            }

            CurrentWeatherScreenEvents.HidePermissionDialog -> {
                _state.value = state.value.copy(
                    isPermissionDialogVisible = false
                )
            }

            CurrentWeatherScreenEvents.FetchWeatherHistory -> {
                fetchWeatherHistory()
            }

            CurrentWeatherScreenEvents.Logout -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        isLogoutDialogVisible = false,
                        isLoadingDialogVisible = true
                    )
                    delay(1500)
                    logout()
                }
            }

            CurrentWeatherScreenEvents.ShowLogoutDialog -> {
                _state.value = state.value.copy(
                    isLogoutDialogVisible = true
                )
            }

            CurrentWeatherScreenEvents.HideLogoutDialog -> {
                _state.value = state.value.copy(
                    isLogoutDialogVisible = false
                )
            }
        }
    }

    private fun getCurrentWeather(
        lat: String,
        lon: String,
        units: String,
        apiKey: String
    ) {

        weatherUseCases.getCurrentWeather.execute(
            latitude = lat,
            longitude = lon,
            units = units,
            apiKey = apiKey,
            firebaseUid = firebaseAuth.currentUser?.uid
        ).onEach {
            when (it) {
                is Resource.Loading -> {
                    Log.d(TAG, "Loading")
                }

                is Resource.Error -> {
                    Log.d(TAG, "Error ${it.message}")

                    _state.value = state.value.copy(
                        error = it.message,
                        isLoading = false
                    )
                }

                is Resource.Success -> {
                    Log.d(TAG, " Success ${it.data}")
                    _state.value = state.value.copy(
                        currentWeatherData = it.data,
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchWeatherHistory() {
        weatherUseCases.getWeatherHistory.execute(
            firebaseAuth.currentUser?.uid
        ).onEach {
            _state.value = state.value.copy(
                weatherHistoryList = it
            )
        }.launchIn(viewModelScope)
    }

    private fun logout() {
        firebaseAuth.signOut()
        _state.value = CurrentWeatherScreenState()
    }

}