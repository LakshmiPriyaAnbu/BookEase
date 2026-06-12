package com.bookease.app.ui.screens.contact

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    var name    by mutableStateOf("")
        private set
    var email   by mutableStateOf("")
        private set
    var message by mutableStateOf("")
        private set

    var nameError    by mutableStateOf<String?>(null)
        private set
    var emailError   by mutableStateOf<String?>(null)
        private set
    var messageError by mutableStateOf<String?>(null)
        private set

    private val _submitEvent = MutableSharedFlow<Unit>()
    val submitEvent: SharedFlow<Unit> = _submitEvent

    fun onNameChange(value: String) {
        name = value
        if (nameError != null) nameError = null
    }

    fun onEmailChange(value: String) {
        email = value
        if (emailError != null) emailError = null
    }

    fun onMessageChange(value: String) {
        message = value
        if (messageError != null) messageError = null
    }

    fun onSubmit() {
        var valid = true

        if (name.isBlank()) {
            nameError = "Name is required"
            valid = false
        } else {
            nameError = null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Enter a valid email address"
            valid = false
        } else {
            emailError = null
        }

        if (message.length < 10) {
            messageError = "Message must be at least 10 characters"
            valid = false
        } else {
            messageError = null
        }

        if (valid) {
            viewModelScope.launch {
                _submitEvent.emit(Unit)
            }
        }
    }
}
