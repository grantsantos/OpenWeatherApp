package com.openweatherapp.feaure_auth.presentation.sign_up

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.openweatherapp.common.Resource
import com.openweatherapp.feaure_auth.domain.ValidateLoginFields
import com.openweatherapp.navigation.AppRouter
import com.openweatherapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun onEvent(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.ValidateFields -> {
                validateFields()
            }

            is SignUpEvents.onEmailChanged -> {
                _state.value = state.value.copy(
                    email = event.email
                )
            }
            is SignUpEvents.onPasswordChanged -> {
                _state.value = state.value.copy(
                    password = event.password
                )
            }
        }
    }

    private fun validateFields() {
        ValidateLoginFields.execute(
            email = state.value.email,
            password = state.value.password
        ).onEach {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    if (it.throwable is ValidateLoginFields.InvalidFieldException) {
                        val fieldType = it.throwable.message
                        if (fieldType == "email") {
                            _state.value = state.value.copy(
                                emailHasError = true,
                                emailErrorMessage = it.message.orEmpty()
                            )
                        }

                        if (fieldType == "password") {
                            _state.value = state.value.copy(
                                passwordHasError = true,
                                passwordErrorMessage = it.message.orEmpty()
                            )
                        }
                    }
                }
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        emailHasError = false,
                        emailErrorMessage = "",
                        passwordHasError = false,
                        passwordErrorMessage = ""
                    )

                    signUp()
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun signUp() {
        _state.value = state.value.copy(
            signUpProgress = true
        )

        firebaseAuth.createUserWithEmailAndPassword(
                state.value.email,
                state.value.password
            )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _state.value = SignUpState()
                    AppRouter.navigateTo(
                        destination = Screen.WeatherScreen
                    )
                }
            }
            .addOnFailureListener {
                _state.value = state.value.copy(
                    signUpProgress = false,
                    signUpError = it.message ?: "Something went wrong"
                )
            }
    }
}