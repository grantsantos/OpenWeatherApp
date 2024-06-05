package com.openweatherapp.feaure_auth.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.openweatherapp.R
import com.openweatherapp.feature_weather.presentation.current_weather.CurrentWeatherScreenEvents
import com.openweatherapp.feaure_auth.presentation.login.LoginState
import com.openweatherapp.navigation.AppRouter
import com.openweatherapp.navigation.Screen
import com.openweatherapp.navigation.SystemBackButtonHandler
import com.openweatherapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    state: SignUpState,
    onEmailTextChanged: (SignUpEvents) -> Unit,
    onPasswordTextChanged: (SignUpEvents) -> Unit,
    onSignUpButtonClicked: (SignUpEvents) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(colorResource(id = R.color.white))
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp),
                    onClick = {
                        AppRouter.navigateTo(
                            Screen.LoginScreen
                        )
                    }) {
                    Icon(
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp),
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Log out"
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White)
                        .padding(start = 28.dp, top = 0.dp, end = 28.dp, bottom = 10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 40.dp),
                        text = "Create an account",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Normal
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val emailValue = remember {
                        mutableStateOf("")
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.email),
                                contentDescription = ""
                            )
                        },
                        label = {
                            Text(text = "Email")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Purple40,
                            focusedLabelColor = Purple40,
                            cursorColor = Purple40
                        ),
                        value = emailValue.value,
                        onValueChange = { newText ->
                            emailValue.value = newText
                            onEmailTextChanged(SignUpEvents.onEmailChanged(newText))
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        maxLines = 1,
                        isError = state.emailHasError
                    )

                    if (state.emailHasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.emailErrorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    val password = remember {
                        mutableStateOf("")
                    }

                    val passwordVisible = remember {
                        mutableStateOf(false)
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.lock),
                                contentDescription = ""
                            )
                        },
                        trailingIcon = {
                            val passwordPainterResource = if (passwordVisible.value) {
                                painterResource(id = R.drawable.password_visibility_on)
                            } else {
                                painterResource(id = R.drawable.password_visibility_off)
                            }

                            IconButton(onClick = {
                                passwordVisible.value = !passwordVisible.value
                            }
                            ) {
                                Icon(painter = passwordPainterResource, contentDescription = "passwordVisibility")
                            }
                        },
                        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                        label = {
                            Text(text = "Password")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Purple40,
                            focusedLabelColor = Purple40,
                            cursorColor = Purple40
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        maxLines = 1,
                        value = password.value,
                        isError = state.passwordHasError,
                        onValueChange = { newText ->
                            password.value = newText
                            onPasswordTextChanged(SignUpEvents.onPasswordChanged(newText))
                        })

                    if (state.passwordHasError) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = state.passwordErrorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (state.signUpProgress) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = Purple40
                        )
                    } else {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp,
                                pressedElevation = 6.dp
                            ),
                            contentPadding = PaddingValues(),
                            onClick = { onSignUpButtonClicked(SignUpEvents.ValidateFields) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(48.dp)
                                    .background(Purple40),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Submit",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        if (state.signUpError.isNotEmpty()) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.signUpError,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }


                }
            }
        }

        SystemBackButtonHandler {
            AppRouter.navigateTo(
                destination = Screen.LoginScreen
            )
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen(
        state = SignUpState(signUpProgress = false),
        onEmailTextChanged = {},
        onPasswordTextChanged = {},
        onSignUpButtonClicked = {}
    )
}