package com.openweatherapp.feaure_auth.presentation.login

sealed class LoginEvents {
    data class onEmailChanged(val email: String) : LoginEvents()
    data class onPasswordChanged(val password:String) : LoginEvents()
    object ValidateFields : LoginEvents()
}