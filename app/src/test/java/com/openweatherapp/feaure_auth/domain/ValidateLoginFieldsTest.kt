package com.openweatherapp.feaure_auth.domain

import com.google.common.truth.Truth.assertThat
import com.openweatherapp.common.Resource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidateLoginFieldsTest {

    @Test
    fun `Test when validation passed`() = runBlocking {
        val email = "test@gmail.com"
        val password ="123456"

        val emissions = ValidateLoginFields.execute(email, password).toList()

        assertThat(emissions[0]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `Test when email is empty validation should fail`() = runBlocking {
        val email = ""
        val password ="123456"

        val emissions = ValidateLoginFields.execute(email, password).toList()

        assertThat(emissions[0]).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `Test when email is invalid validation should fail`() = runBlocking {
        val email = "testtest"
        val password ="123456"

        val emissions = ValidateLoginFields.execute(email, password).toList()

        assertThat(emissions[0]).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `Test when password is empty validation should fail`() = runBlocking {
        val email = "test@gmail.com"
        val password =""

        val emissions = ValidateLoginFields.execute(email, password).toList()

        assertThat(emissions[0]).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `Test when password is invalid validation should fail`() = runBlocking {
        val email = "test@gmail.com"
        val password ="12345"

        val emissions = ValidateLoginFields.execute(email, password).toList()

        assertThat(emissions[0]).isInstanceOf(Resource.Error::class.java)
    }
}