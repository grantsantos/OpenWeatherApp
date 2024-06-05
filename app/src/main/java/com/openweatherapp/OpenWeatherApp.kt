package com.openweatherapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.openweatherapp.common.EncryptionUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OpenWeatherApp : Application()