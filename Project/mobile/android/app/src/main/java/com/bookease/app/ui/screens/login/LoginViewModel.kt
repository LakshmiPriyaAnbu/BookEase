package com.bookease.app.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var email    by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    var emailError    by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    var keepLoggedIn by mutableStateOf(false)
        private set

    private val _loginEvent = MutableSharedFlow<Unit>()
    val loginEvent: SharedFlow<Unit> = _loginEvent

    fun onEmailChange(value: String) {
        email = value
        if (emailError != null) emailError = null
    }

    fun onPasswordChange(value: String) {
        password = value
        if (passwordError != null) passwordError = null
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun onKeepLoggedInChange(value: Boolean) {
        keepLoggedIn = value
    }

    fun onLogin() {
        var valid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Enter a valid email address"
            valid = false
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "Password is required"
            valid = false
        } else {
            passwordError = null
        }

        if (valid) {
            viewModelScope.launch {
                _loginEvent.emit(Unit)
            }
        }
    }
}
