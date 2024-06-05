package com.openweatherapp.feature_weather.domain.model.current_weather

import com.openweatherapp.feature_weather.data.local.model.WeatherEntity
import com.openweatherapp.feature_weather.domain.model.DateData

data class CurrentWeather(
    val temperature: Double?,
    val humidity: Int?,
    val feelsLike: Double?,
    val countryCode: String?,
    val cityName: String?,
    val sunrise: Long?,
    val sunset: Long?,
    val image: String?,

    //not included in the response
    val dateData: DateData
) {
    fun toWeatherEntity(uID: String?): WeatherEntity {
        return WeatherEntity(
            System.currentTimeMillis(),
            uID,
            dateData.day,
            dateData.date,
            temperature,
            cityName,
            countryCode,
            image
        )
    }
}