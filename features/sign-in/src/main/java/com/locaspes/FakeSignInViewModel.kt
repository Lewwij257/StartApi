package com.locaspes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FakeSignInViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun signIn(){
    }

    fun updatePassword(password: String){
        _uiState.update { it.copy(password = password) }
    }

    fun updateEmailOrUsername(emailOrUsername: String){
        _uiState.update { it.copy(emailOrUsername = emailOrUsername) }
    }
}