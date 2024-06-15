package com.openweatherapp.feaure_auth.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.openweatherapp.common.Resource
import com.openweatherapp.feaure_auth.domain.ValidateLoginFields
import com.openweatherapp.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.ValidateFields -> {
                validateFields()
            }

            is LoginEvents.onEmailChanged -> {
                _state.value = state.value.copy(
                    email = event.email
                )
            }
            is LoginEvents.onPasswordChanged -> {
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
                    Log.d("LoginViewModel", "pasok si gago")
                    _state.value = state.value.copy(
                        emailHasError = false,
                        emailErrorMessage = "",
                        passwordHasError = false,
                        passwordErrorMessage = ""
                    )

                    login()
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun login() {
        _state.value = state.value.copy(
            loginInProgress = true
        )

        firebaseAuth.signInWithEmailAndPassword(
                state.value.email,
                state.value.password
            )
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _state.value = LoginState()
                }
                _state.value = state.value.copy(
                    loginInProgress = false
                )
            }
            .addOnFailureListener {
                _state.value = state.value.copy(
                    loginInProgress = false,
                    loginError = it.message ?: "Something went wrong"
                )
            }
    }

}