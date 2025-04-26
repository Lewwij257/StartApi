package com.locaspes.startapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.data.model.UserProfile
import com.locaspes.utils.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase): ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String){
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun updateUsername(username: String){
        _uiState.update { it.copy(username = username, errorMessage = null) }
    }

    fun updatePassword(password: String){
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun signUp(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val signUpResult = signUpUseCase.signUp(
                UserProfile(
                    email = _uiState.value.email,
                    username = _uiState.value.username,
                    password = _uiState.value.password
                )
            )
            if (signUpResult.isSuccess){
                _uiState.update { it.copy(isLoading = false, isSignUpSuccessful = true, errorMessage = null) }
            }
            else{
                _uiState.update { it.copy(isLoading = false, errorMessage = signUpResult.exceptionOrNull()?.message)}
            }
        }
    }
}