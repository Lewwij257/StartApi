package com.locaspes.startapi

import com.locaspes.utils.AuthValidationError

sealed interface SignUpUiState{

    val email: String
    val username: String
    val password: String
    val validationErrors: List<AuthValidationError>
    val message: String


    data class Idle(
        override val email: String = "",
        override val username: String = "",
        override val password: String = "",
        override val validationErrors: List<AuthValidationError> = emptyList(),
        override val message: String = ""
    ): SignUpUiState

    data class Loading(
        override val email: String,
        override val username: String,
        override val password: String,
        override val validationErrors: List<AuthValidationError> = emptyList(),
        override val message: String = ""
    ): SignUpUiState

    data class Success(
        val userId: String,
        override val email: String = "",
        override val username: String = "",
        override val password: String = "",
        override val validationErrors: List<AuthValidationError> = emptyList(),
        override val message: String = ""
    ) : SignUpUiState

    data class Error(
        override val email: String,
        override val username: String,
        override val password: String,
        override val message: String,
        override val validationErrors: List<AuthValidationError> = emptyList()
    ): SignUpUiState
}