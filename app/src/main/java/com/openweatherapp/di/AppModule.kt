package com.openweatherapp.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.openweatherapp.common.Constants
import com.openweatherapp.common.EncryptionUtil
import com.openweatherapp.data.local.AppDatabase
import com.openweatherapp.data.network.OpenWeatherApi
import com.openweatherapp.feature_weather.data.repository.WeatherRepositoryImpl
import com.openweatherapp.feature_weather.domain.repository.WeatherRepository
import com.openweatherapp.feature_weather.domain.use_case.GetCurrentWeather
import com.openweatherapp.feature_weather.domain.use_case.GetWeatherHistory
import com.openweatherapp.feature_weather.domain.use_case.WeatherUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: OpenWeatherApi,
        db: AppDatabase
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideWeatherUseCases(
        repository: WeatherRepository
    ) : WeatherUseCases {
        return WeatherUseCases(
            getCurrentWeather = GetCurrentWeather(repository),
            getWeatherHistory = GetWeatherHistory(repository)
        )
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application) : AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db"
        )
            .openHelperFactory(EncryptionUtil.createSupportFactory(app))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}