package com.locaspes.utils

sealed class AuthValidationResult {
    data object Success: AuthValidationResult()
    data class Failure(val errors: List<AuthValidationError>): AuthValidationResult()
}

sealed class AuthValidationError{
    data object EmptyEmail : AuthValidationError()
    data object InvalidEmail : AuthValidationError()
    data object EmptyUsername : AuthValidationError()
    data object ShortUsername : AuthValidationError()
    data object LongUsername : AuthValidationError()
    data object ShortPassword : AuthValidationError()
    data object EmptyPassword : AuthValidationError()
    data object EmptyEmailOrUsername : AuthValidationError()
}

