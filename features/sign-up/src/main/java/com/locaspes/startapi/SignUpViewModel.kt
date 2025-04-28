package com.locaspes.startapi

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.model.AuthResult
import com.locaspes.data.model.UserProfile
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

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()



    fun updateEmail(email: String) {
        _uiState.update { state ->
            when (state) {
                is SignUpUiState.Idle -> state.copy(email = email, validationErrors = emptyList())
                is SignUpUiState.Error -> SignUpUiState.Idle(
                    email = email,
                    username = state.username,
                    password = state.password,
                    validationErrors = emptyList(),
                    message = ""
                )

                is SignUpUiState.Loading -> state
                is SignUpUiState.Success -> state
            }
        }
    }

    fun updateUsername(username: String) {
        _uiState.update { state ->
            when (state) {
                is SignUpUiState.Idle -> state.copy(username = username, validationErrors = emptyList())

                is SignUpUiState.Error -> SignUpUiState.Idle(
                    email = state.email,
                    username = username,
                    password = state.password,
                    validationErrors = emptyList(),
                    message = ""
                )

                is SignUpUiState.Loading -> state
                is SignUpUiState.Success -> state
            }
        }
    }

    fun updatePassword(password: String) {
        _uiState.update { state ->
            when (state) {
                is SignUpUiState.Idle -> state.copy(
                    password = password,
                    validationErrors = emptyList()
                )
                is SignUpUiState.Error -> SignUpUiState.Idle(
                    email = state.email,
                    username = state.username,
                    password = password,
                    validationErrors = emptyList(),
                    message = ""
                )
                is SignUpUiState.Loading -> state
                is SignUpUiState.Success -> state
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun signUp() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val (email, username, password) = Triple(
                currentState.email,
                currentState.username,
                currentState.password
            )

            _uiState.update {
                SignUpUiState.Loading(
                    email = email,
                    username = username,
                    password = password
                )
            }

            val result = signUpUseCase.signUp(
                UserProfile(
                    email = email,
                    username = username,
                    password = password
                )
            )

            _uiState.update {
                when (result) {
                    is AuthResult.Success -> SignUpUiState.Success(result.userId)
                    is AuthResult.ValidationFailure -> SignUpUiState.Error(
                        email = email,
                        username = username,
                        password = password,
                        message = "Пожалуйста, исправьте ошибки в полях",
                        validationErrors = result.validationResult.errors
                    )

                    is AuthResult.NetworkError -> SignUpUiState.Error(
                        email = email,
                        username = username,
                        password = password,
                        message = result.message,
                        validationErrors = emptyList()
                    )

                    is AuthResult.AuthenticationError -> SignUpUiState.Error(
                        email = email,
                        username = username,
                        password = password,
                        message = result.message,
                        validationErrors = emptyList()
                    )

                    is AuthResult.UnknownError -> SignUpUiState.Error(
                        email = email,
                        username = username,
                        password = password,
                        message = result.message,
                        validationErrors = emptyList()
                    )
                }
            }
        }
    }
}

