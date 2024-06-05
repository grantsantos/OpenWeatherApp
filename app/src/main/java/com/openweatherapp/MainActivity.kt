package com.openweatherapp

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.openweatherapp.common.Constants
import com.openweatherapp.common.DefaultLocationClient
import com.openweatherapp.common.LocationClient
import com.openweatherapp.common.openAppSettings
import com.openweatherapp.ui.components.LogoutConfirmation
import com.openweatherapp.ui.components.PermissionDialog
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenEvents
import com.openweatherapp.feature_weather.presentation.current_weather.WeatherViewModel
import com.openweatherapp.feaure_auth.presentation.login.LoginViewModel
import com.openweatherapp.feaure_auth.presentation.sign_up.SignUpViewModel
import com.openweatherapp.navigation.AppRouter
import com.openweatherapp.navigation.OpenWeatherAppNav
import com.openweatherapp.navigation.Screen
import com.openweatherapp.ui.theme.OpenWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.security.Permission
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    private lateinit var locationClient: LocationClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var authStateListener : AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebaseAuthListener()

        locationClient = DefaultLocationClient(
            this,
            LocationServices.getFusedLocationProviderClient(this)
        )
        setContent {
            OpenWeatherAppTheme {
                val viewModel = hiltViewModel<WeatherViewModel>()
                val loginViewModel = hiltViewModel<LoginViewModel>()
                val signUpViewModel = hiltViewModel<SignUpViewModel>()

                RequestLocationPermission(viewModel)

                if (viewModel.state.value.isPermissionDialogVisible) {
                    PermissionDialog(
                        isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        onDismiss = {
                            viewModel.onEvent(CurrentWeatherScreenEvents.HidePermissionDialog)
                            finish()
                        },
                        onOnGrantPermission = {
                            viewModel.onEvent(CurrentWeatherScreenEvents.HidePermissionDialog)
                        },
                        onGoToAppSettingsClick = {
                            openAppSettings()
                        }
                    )
                }

                OpenWeatherAppNav(
                    firebaseAuth  = firebaseAuth,
                    weatherViewModel = viewModel,
                    getCurrentLocation = {
                        getCurrentLocation(viewModel)
                    },
                    loginViewModel = loginViewModel,
                    signUpViewModel = signUpViewModel
                )
            }
        }
    }

    private fun getCurrentLocation(
        viewModel: WeatherViewModel
    ) {
        locationClient
            .getLocationUpdates(1000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                val lat = location.latitude.toString()
                val lon = location.longitude.toString()

                viewModel.onEvent(
                    CurrentWeatherScreenEvents.GetCurrentWeather(
                        lat,
                        lon,
                        "metric",
                        Constants.API_KEY
                    )
                )
            }
            .launchIn(lifecycleScope)
    }

    @Composable
    private fun RequestLocationPermission(viewModel: WeatherViewModel) {
        var allPermissionsGranted by remember { mutableStateOf(false) }

        val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                allPermissionsGranted = permissions.entries.all { it.value }

                if (!allPermissionsGranted) {
                    viewModel.onEvent(CurrentWeatherScreenEvents.ShowPermissionDialog)
                }
            }
        )

        LaunchedEffect(Unit) {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
        }
    }

    private fun initFirebaseAuthListener() {
        authStateListener = AuthStateListener {
            checkForActiveSession(it.currentUser)

            if (it.currentUser == null) {
                AppRouter.navigateTo(
                    Screen.LoginScreen
                )

                Log.d(TAG, " FirbaseAuthStateListener: User signed out")
            } else {
                AppRouter.navigateTo(
                    Screen.WeatherScreen
                )
                Log.d(TAG, " FirbaseAuthStateListener: User signed in")
            }
        }
    }

    private fun checkForActiveSession(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            AppRouter.navigateTo(
                Screen.LoginScreen
            )
        } else {
            AppRouter.navigateTo(
                Screen.WeatherScreen
            )
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}