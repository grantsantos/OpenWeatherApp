package com.openweatherapp.ui.components

import android.graphics.Paint.Align
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser
import com.openweatherapp.R
import com.openweatherapp.feature_weather.domain.model.DateData
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreen
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenEvents
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenState
import com.openweatherapp.feature_weather.presentation.weather_history.WeatherHistoryScreen
import com.openweatherapp.ui.theme.Purple40
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabbedScreen(
    state: CurrentWeatherScreenState,
    firebaseUser: FirebaseUser?,
    onGetWeatherHistory: (CurrentWeatherScreenEvents) -> Unit,
    onLogoutButtonClicked: (CurrentWeatherScreenEvents) -> Unit,
    onConfirmationClicked: (CurrentWeatherScreenEvents) -> Unit,
    onCancelClicked: (CurrentWeatherScreenEvents) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState(
            pageCount = { 2 }
        )
        val coroutineScope = rememberCoroutineScope()

        if (state.isLogoutDialogVisible) {
            LogoutConfirmation(
                onDismiss = {
                    onCancelClicked(CurrentWeatherScreenEvents.HideLogoutDialog)
                },
                onConfirmation = {
                    onConfirmationClicked(CurrentWeatherScreenEvents.Logout)
                }
            )
        }

        if (state.isLoadingDialogVisible) {
            LoadingDialog()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp),
            ) {
                Text(
                    text = "Welcome, ",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                )
                Text(
                    text = "${firebaseUser?.email}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        color = Purple40
                    )
                )
            }

            IconButton(
                onClick = {
                    onLogoutButtonClicked(CurrentWeatherScreenEvents.ShowLogoutDialog)
                }) {
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Log out"
                )
            }
        }
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White
        ) {
            Tab(
                modifier = Modifier.height(50.dp),
                selected = pagerState.currentPage == 0,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }) {
                Text(text = "Current Weather")
            }

            Tab(
                modifier = Modifier.height(50.dp),
                selected = pagerState.currentPage == 1,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }) {
                Text(text = "Weather History")

            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    CurrentWeatherScreen(state = state)
                }

                1 -> {
                    onGetWeatherHistory(CurrentWeatherScreenEvents.FetchWeatherHistory)
                    WeatherHistoryScreen(weatherList = state.weatherHistoryList)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TabbedScreenPreview() {
    TabbedScreen(
        state = CurrentWeatherScreenState(
            currentWeatherData = CurrentWeather(
                temperature = 32.65,
                humidity = 70,
                feelsLike = 39.65,
                countryCode = "PH",
                cityName = "Quezon City",
                sunrise = 1717363545,
                sunset = 1717410148,
                image = "02d",
                dateData = DateData(
                    "Monday",
                    "June 1, 2024"
                )
            ),
            isLoading = false
        ),
        firebaseUser = null,
        onGetWeatherHistory = {},
        onLogoutButtonClicked = {},
        onConfirmationClicked = {},
        onCancelClicked = {}
    )
}