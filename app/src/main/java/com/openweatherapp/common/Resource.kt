package com.openweatherapp.common

sealed class Resource<T>(val data: T? = null, val message: String? = null, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, throwable: Throwable? = null) : Resource<T>(data, message, throwable)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}