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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.openweatherapp.common.Constants
import com.openweatherapp.common.DefaultLocationClient
import com.openweatherapp.common.LocationClient
import com.openweatherapp.common.openAppSettings
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenEvents
import com.openweatherapp.feature_weather.presentation.current_weather.WeatherViewModel
import com.openweatherapp.navigation.OpenWeatherAppNav
import com.openweatherapp.navigation.Screen
import com.openweatherapp.ui.components.PermissionDialog
import com.openweatherapp.ui.theme.OpenWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

        locationClient = DefaultLocationClient(
            this,
            LocationServices.getFusedLocationProviderClient(this)
        )
        setContent {
            OpenWeatherAppTheme {
                val navHostController = rememberNavController()
                InitFirebaseAuthListener(navHostController)
                RequestLocationPermission()
                SetUpPermissionDialog()

                OpenWeatherAppNav(
                    navHostController = navHostController,
                    firebaseAuth = firebaseAuth,
                    getCurrentLocation = {
                        getCurrentLocation(it)
                    }
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
    private fun RequestLocationPermission(viewModel: WeatherViewModel = hiltViewModel()) {
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

    @Composable
    private fun SetUpPermissionDialog(viewModel: WeatherViewModel = hiltViewModel()) {
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
    }

    @Composable
    private fun InitFirebaseAuthListener(
        navHostController: NavHostController
    ) {
        authStateListener = AuthStateListener {
            checkForActiveSession(
                navHostController = navHostController,
                        it.currentUser
            )

            if (it.currentUser == null) {
                Log.d(TAG, " FirbaseAuthStateListener: User signed out")
            } else {
                Log.d(TAG, " FirbaseAuthStateListener: User signed in")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    private fun checkForActiveSession(
        navHostController: NavHostController,
        currentUser: FirebaseUser?
    ) {
        if (currentUser == null) {
            navHostController.navigate(
                Screen.LoginScreen
            )
        } else {
            navHostController.navigate(
                Screen.WeatherScreen
            )
        }
    }

    override fun onStop() {
        super.onStop()
        if (::authStateListener.isInitialized) {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }
}

val NavHostController.canGoBack : Boolean
    get() = currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

fun NavHostController.navigateBack() {
    if (canGoBack) {
        popBackStack()
    }
}