package com.openweatherapp.feature_weather.data.remote.model


import com.google.gson.annotations.SerializedName
import com.openweatherapp.feature_weather.domain.model.DateData
import com.openweatherapp.feature_weather.domain.model.current_weather.CurrentWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CurrentWeatherDto(
    @SerializedName("coord")
    val coord: CoordDto?,
    @SerializedName("weather")
    val weather: List<WeatherDto>?,
    @SerializedName("base")
    val base: String?,
    @SerializedName("main")
    val main: MainDto?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("wind")
    val wind: WindDto?,
    @SerializedName("clouds")
    val clouds: CloudsDto?,
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("sys")
    val sys: SysDto?,
    @SerializedName("timezone")
    val timezone: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("cod")
    val cod: Int?
) {
    fun toCurrentWeather() : CurrentWeather {

        val date = Date()
        val dayFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH)
        val dayOfWeek = dayFormat.format(date)
        val formattedDate = dateFormat.format(date)
        val dateData = DateData(day = dayOfWeek, date = formattedDate)


        return CurrentWeather(
            temperature = main?.temp,
            humidity = main?.humidity,
            feelsLike = main?.feelsLike,
            countryCode = sys?.country,
            cityName = name,
            sunrise = sys?.sunrise,
            sunset = sys?.sunset,
            image = weather?.firstOrNull()?.icon,
            dateData = dateData
        )
    }
}