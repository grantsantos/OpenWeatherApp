package com.openweatherapp.feaure_auth.domain

import com.openweatherapp.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object ValidateLoginFields {

    fun execute(email: String, password: String) : Flow<Resource<Unit>> = flow {

        if (email.isEmpty()) {
            emit(Resource.Error(message = "Email can't be blank", data = null, throwable = InvalidFieldException("email")))
            return@flow
        }

        if (!email.contains("@")) {
            emit(Resource.Error(message = "Invalid Email", data = null, throwable = InvalidFieldException("email")))
            return@flow
        }

        if (password.length < 6) {
            emit(Resource.Error(message = "Password should be at least 6 characters", data = null, throwable = InvalidFieldException("password")))
            return@flow
        }

        emit(Resource.Success(Unit))
    }

    class InvalidFieldException(fieldName: String) : Exception(fieldName)
}