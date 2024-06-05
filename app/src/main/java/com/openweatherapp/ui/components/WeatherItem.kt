package com.openweatherapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.openweatherapp.common.Constants
import com.openweatherapp.feature_weather.domain.model.DateData
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenState
import java.util.Locale

@Composable
fun WeatherItem(currentWeatherData: CurrentWeather?) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = currentWeatherData?.dateData?.day ?: "Day Unknown",
                style = TextStyle(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Normal
                ),
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = currentWeatherData?.dateData?.date ?: "Date Unknown",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    fontStyle = FontStyle.Normal
                ),
            )


            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    Row {
                        Text(
                            text = "${currentWeatherData?.temperature ?: "Temp"}",
                            style = TextStyle(
                                fontSize = 44.sp,
                                fontWeight = FontWeight.W400,
                                fontStyle = FontStyle.Normal,
                            ),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "°C",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W400,
                                fontStyle = FontStyle.Normal,
                            ),
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        text = (currentWeatherData?.cityName ?: "Location Unknown") + ",",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            fontStyle = FontStyle.Normal
                        )
                    )

                    val locale = Locale("", currentWeatherData?.countryCode.orEmpty())

                    Text(
                        text = locale.displayCountry,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            fontStyle = FontStyle.Normal
                        )
                    )
                }


                AsyncImage(
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    model = Constants.getCompleteImageUrl(currentWeatherData?.image.orEmpty()),
                    contentDescription = "sdf",
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}

@Preview
@Composable
private fun WeatherItemPreview() {
    WeatherItem(
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
        )
    )
}