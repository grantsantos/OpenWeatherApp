package com.openweatherapp.navigation

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.openweatherapp.common.Constants
import com.openweatherapp.ui.components.TabbedScreen
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenEvents
import com.openweatherapp.feature_weather.presentation.current_weather.WeatherViewModel
import com.openweatherapp.feaure_auth.presentation.login.LoginScreen
import com.openweatherapp.feaure_auth.presentation.login.LoginViewModel
import com.openweatherapp.feaure_auth.presentation.sign_up.SignUpScreen
import com.openweatherapp.feaure_auth.presentation.sign_up.SignUpViewModel

@Composable
fun OpenWeatherAppNav(
    firebaseAuth: FirebaseAuth,
    weatherViewModel: WeatherViewModel,
    getCurrentLocation: () -> Unit,
    loginViewModel: LoginViewModel,
    signUpViewModel: SignUpViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = AppRouter.currentScreen, label = "") { currentScreen ->
            when (currentScreen.value) {
                Screen.LoginScreen -> {
                    LoginScreen(
                        state = loginViewModel.state.value,
                        onEmailTextChanged = loginViewModel::onEvent,
                        onPasswordTextChanged = loginViewModel::onEvent,
                        onLoginButtonClicked = loginViewModel::onEvent
                    )

                }
                Screen.SignUpScreen -> {
                    SignUpScreen(
                        state = signUpViewModel.state.value,
                        onEmailTextChanged = signUpViewModel::onEvent,
                        onPasswordTextChanged = signUpViewModel::onEvent,
                        onSignUpButtonClicked = signUpViewModel::onEvent
                    )
                }
                Screen.WeatherScreen -> {
                    if (firebaseAuth.currentUser != null) {
                        if (weatherViewModel.state.value.currentWeatherData == null) {
                            getCurrentLocation()
                        }
                    }

                    TabbedScreen(
                        state = weatherViewModel.state.value,
                        firebaseUser = firebaseAuth.currentUser,
                        onGetWeatherHistory = weatherViewModel::onEvent,
                        onLogoutButtonClicked = weatherViewModel::onEvent,
                        onConfirmationClicked = weatherViewModel::onEvent,
                        onCancelClicked = weatherViewModel::onEvent,
                    )
                }
            }
        }
    }
}