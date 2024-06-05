package com.openweatherapp.common.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.openweatherapp.feature_weather.data.local.dao.WeatherHistoryDao
import com.openweatherapp.feature_weather.data.local.model.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWeatherHistoryDao() : WeatherHistoryDao
}