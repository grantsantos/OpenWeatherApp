package com.openweatherapp.feature_weather.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.openweatherapp.feature_weather.domain.model.DateData
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather

@Entity
data class WeatherEntity(
    @PrimaryKey val id: Long,
    val uID: String?,
    val day: String,
    val date: String,
    val temperature: Double?,
    val cityName: String?,
    val countryCode: String?,
    val image: String?
) {
    fun toCurrentWeather(): CurrentWeather {
        return CurrentWeather(
            temperature = temperature,
            cityName = cityName,
            countryCode = countryCode,
            image = image,
            dateData = DateData(
                day,
                date
            ),
            feelsLike = null,
            humidity = null,
            sunset = null,
            sunrise = null
        )
    }
}