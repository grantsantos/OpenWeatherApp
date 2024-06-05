package com.openweatherapp.feature_weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openweatherapp.feature_weather.data.local.model.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weatherentity WHERE uID = :uID ORDER BY id DESC")
    fun getWeatherHistory(uID: String?) : Flow<List<WeatherEntity>>

}