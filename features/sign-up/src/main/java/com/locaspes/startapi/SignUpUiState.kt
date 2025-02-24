package com.locaspes.startapi

data class SignUpUiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpSuccessful: Boolean = false
)