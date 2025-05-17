package com.locaspes.startapi

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)