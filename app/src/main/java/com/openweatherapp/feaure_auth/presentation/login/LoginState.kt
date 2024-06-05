package com.openweatherapp.feaure_auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",

    val emailHasError: Boolean = false,
    val emailErrorMessage: String = "",
    val passwordHasError: Boolean = false,
    val passwordErrorMessage: String = "",

    val loginInProgress: Boolean = false,
    val loginError: String = ""
)