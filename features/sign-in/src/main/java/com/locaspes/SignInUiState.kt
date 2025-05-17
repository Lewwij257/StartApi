package com.locaspes

data class SignInUiState(
    val emailOrUsername: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)