package com.openweatherapp.feaure_auth.presentation.sign_up

sealed class SignUpEvents {
    data class onEmailChanged(val email: String) : SignUpEvents()
    data class onPasswordChanged(val password:String) : SignUpEvents()
    object ValidateFields : SignUpEvents()
}