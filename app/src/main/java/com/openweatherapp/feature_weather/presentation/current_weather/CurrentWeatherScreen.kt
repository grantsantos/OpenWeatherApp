package com.openweatherapp.feature_weather.presentation.current_weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openweatherapp.feature_weather.domain.model.DateData
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.ui.components.FeelsLikeItem
import com.openweatherapp.ui.components.HumidityItem
import com.openweatherapp.ui.components.SunriseItem
import com.openweatherapp.ui.components.SunsetItem
import com.openweatherapp.ui.components.WeatherItem
import com.openweatherapp.ui.theme.Purple40

@Composable
fun CurrentWeatherScreen(
    state: CurrentWeatherScreenState
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .verticalScroll(state = scrollState)
    ) {
        if (state.isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Purple40
                )
            }
        } else {
            Column {
                WeatherItem(currentWeatherData = state.currentWeatherData)

                Spacer(modifier = Modifier.height(14.dp))

                Row {
                    HumidityItem(modifier = Modifier.weight(1f), humidity = state.currentWeatherData?.humidity)
                    Spacer(modifier = Modifier.width(10.dp))
                    FeelsLikeItem(modifier = Modifier.weight(1f), feelsLike = state.currentWeatherData?.feelsLike)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row {
                    SunriseItem(modifier = Modifier.weight(1f), sunriseTimeInMillis = state.currentWeatherData?.sunrise)
                    Spacer(modifier = Modifier.width(10.dp))
                    SunsetItem(modifier = Modifier.weight(1f), sunsetTimeInMillis = state.currentWeatherData?.sunset)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CurrentWeatherScreenPreview() {
    CurrentWeatherScreen(
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
        )
    )
}