package com.locaspes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCase: SignInUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun signIn(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = "") }
            val signInResult = useCase.signIn(uiState.value.emailOrUsername, uiState.value.password)
            if (signInResult.isSuccess){
                _uiState.update { it.copy(isLoading = false) }
            }
            else{
                _uiState.update {
                    it.copy(
                        errorMessage = signInResult.exceptionOrNull()?.message!!,
                        isLoading = false) }
            }
        }
    }

    fun updatePassword(password: String){
        _uiState.update { it.copy(password = password) }
    }

    fun updateEmailOrUsername(emailOrUsername: String){
        _uiState.update { it.copy(emailOrUsername = emailOrUsername) }
    }

}