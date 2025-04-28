package com.locaspes.data.model

import com.locaspes.utils.AuthValidationResult

sealed class AuthResult {
    data class Success(val userId: String) : AuthResult()
    data class ValidationFailure(val validationResult: AuthValidationResult.Failure) : AuthResult()
    data class NetworkError(val message: String) : AuthResult()
    data class AuthenticationError(val message: String) : AuthResult()
    data class UnknownError(val message: String) : AuthResult()
}