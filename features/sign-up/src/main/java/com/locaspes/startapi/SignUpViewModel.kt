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
open class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase): ViewModel() {

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()


    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun signUp() {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = "")
        }
        viewModelScope.launch {
            val currentState = _uiState.value
            val (email, username, password) = Triple(
                currentState.email,
                currentState.username,
                currentState.password
            )
            val result = signUpUseCase.signUp(
                UserProfile(
                    email = email,
                    username = username,
                    password = password
                )
            )

            when (result){
                is AuthResult.Success -> _uiState.update { it.copy(isLoading = false, errorMessage = "") }
                is AuthResult.AuthentificationError -> _uiState.update { it.copy(isLoading = false, errorMessage = "Неверные данные входа") }
                is AuthResult.NetworkError -> _uiState.update { it.copy(isLoading = false, errorMessage = "Ошибка сети") }
                is AuthResult.UnknownError -> _uiState.update { it.copy(isLoading = false, errorMessage = "Неизвестная ошибка. Она уже отправлена на сервер, скоро всё починим!") }
                is AuthResult.ValidationFailure ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.validationResult.errors.first().toReadable()) }
            }

//
//            _uiState.update {
//                when (result) {
//                    is AuthResult.Success -> SignUpUiState.Success(result.userId)
//                    is AuthResult.ValidationFailure -> SignUpUiState.Error(
//                        email = email,
//                        username = username,
//                        password = password,
//                        message = "Пожалуйста, исправьте ошибки в полях",
//                        validationErrors = result.validationResult.errors
//                    )
//
//                    is AuthResult.NetworkError -> SignUpUiState.Error(
//                        email = email,
//                        username = username,
//                        password = password,
//                        message = result.message,
//                        validationErrors = emptyList()
//                    )
//
//                    is AuthResult.AuthenticationError -> SignUpUiState.Error(
//                        email = email,
//                        username = username,
//                        password = password,
//                        message = result.message,
//                        validationErrors = emptyList()
//                    )
//
//                    is AuthResult.UnknownError -> SignUpUiState.Error(
//                        email = email,
//                        username = username,
//                        password = password,
//                        message = result.message,
//                        validationErrors = emptyList()
//                    )
        }
    }
}

