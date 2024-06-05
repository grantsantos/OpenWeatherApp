package com.openweatherapp.feature_weather.presentation.weather_history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openweatherapp.feature_weather.domain.model.DateData
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.ui.components.WeatherItem

@Composable
fun WeatherHistoryScreen(weatherList: List<CurrentWeather>) {
    LazyColumn(
        modifier = Modifier.padding(14.dp)
    ) {
        items(weatherList) {
            WeatherItem(currentWeatherData = it)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun WeatherHistoryScreenPreview() {
    val list = listOf(
        CurrentWeather(
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
        CurrentWeather(
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
        CurrentWeather(
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
        CurrentWeather(
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
        )
    )

    WeatherHistoryScreen(list)
}
