package com.openweatherapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.openweatherapp.feature_weather.presentation.current_weather.WeatherViewModel
import com.openweatherapp.feaure_auth.presentation.login.LoginScreen
import com.openweatherapp.feaure_auth.presentation.login.LoginViewModel
import com.openweatherapp.feaure_auth.presentation.sign_up.SignUpScreen
import com.openweatherapp.feaure_auth.presentation.sign_up.SignUpViewModel
import com.openweatherapp.ui.components.SplashScreen
import com.openweatherapp.ui.components.TabbedScreen

@Composable
fun OpenWeatherAppNav(
    navHostController: NavHostController,
    firebaseAuth: FirebaseAuth,
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    getCurrentLocation: (WeatherViewModel) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {

    val animationDuration = 900
    val TAG = "OpenWeatherAppNav"

    NavHost(
        navController = navHostController,
        startDestination = Screen.SplashScreen,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(animationDuration))
        },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(animationDuration)) },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(animationDuration))
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(animationDuration))
        }
    ) {
        composable<Screen.SplashScreen> {
            SplashScreen()
        }

        composable<Screen.LoginScreen> {
            LoginScreen(
                navHostController = navHostController,
                state = loginViewModel.state.value,
                onEmailTextChanged = loginViewModel::onEvent,
                onPasswordTextChanged = loginViewModel::onEvent,
                onLoginButtonClicked = loginViewModel::onEvent
            )
        }

        composable<Screen.SignUpScreen> {
            SignUpScreen(
                navHostController = navHostController,
                state = signUpViewModel.state.value,
                onEmailTextChanged = signUpViewModel::onEvent,
                onPasswordTextChanged = signUpViewModel::onEvent,
                onSignUpButtonClicked = signUpViewModel::onEvent
            )
        }

        composable<Screen.WeatherScreen> {
            val user = firebaseAuth.currentUser
            DisposableEffect(user) {
                if (user != null) {
                    if (weatherViewModel.state.value.currentWeatherData == null) {
                        getCurrentLocation(weatherViewModel)
                    }
                }

                onDispose {  }
            }

            TabbedScreen(
                state = weatherViewModel.state.value,
                firebaseUser = firebaseAuth.currentUser,
                onGetWeatherHistory = weatherViewModel::onEvent,
                onLogoutButtonClicked = weatherViewModel::onEvent,
                onConfirmationClicked = weatherViewModel::onEvent,
                onCancelClicked = weatherViewModel::onEvent
            )
        }
    }
}