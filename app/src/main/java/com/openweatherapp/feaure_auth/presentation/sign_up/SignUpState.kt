package com.openweatherapp.feaure_auth.presentation.sign_up

data class SignUpState(
    val email: String = "",
    val password: String = "",

    val emailHasError: Boolean = false,
    val emailErrorMessage: String = "",
    val passwordHasError: Boolean = false,
    val passwordErrorMessage: String = "",

    val signUpProgress: Boolean = false,
    val signUpError: String = ""
)