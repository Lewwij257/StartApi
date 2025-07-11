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

    fun toReadable(): String {
        return when (this) {
            is EmptyEmail -> "Пустое поле email"
            EmptyEmailOrUsername -> "Пустое поле email/имени пользователя"
            EmptyPassword -> "Пустое поле пароля"
            EmptyUsername -> "Пустое поле имени пользователя"
            InvalidEmail -> "Кажется, почта не действительна"
            LongUsername -> "Дружище, имя попроще (покороче)"
            ShortPassword -> "Слишком легкий пароль"
            ShortUsername -> "Маловат никнейм будет"
        }
    }
}

