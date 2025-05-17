package com.locaspes.startapi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeSignUpViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun signUp() {
    }

    fun updatePassword(password: String) {

    }

    fun updateUsername(username: String) {

    }

    fun updateEmail(email: String) {
    }
}