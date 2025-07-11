package com.locaspes.startapi

import com.locaspes.data.model.Message

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)