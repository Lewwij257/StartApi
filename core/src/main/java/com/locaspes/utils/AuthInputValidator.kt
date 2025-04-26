package com.locaspes.utils

import android.text.TextUtils

class AuthInputValidator {

    companion object{

        fun validateSignUpFields(email: String, username: String, password: String): AuthValidationResult{
            val errors = mutableListOf<AuthValidationError>()

            //email
            if (TextUtils.isEmpty(email)){
                errors.add(AuthValidationError.EmptyEmail)
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                errors.add(AuthValidationError.InvalidEmail)
            }

            //password
            if (TextUtils.isEmpty(password)){
                errors.add(AuthValidationError.EmptyPassword)
            }
            else if (password.length < 5){
                errors.add(AuthValidationError.ShortPassword)
            }

            //username
            if (TextUtils.isEmpty(username)){
                errors.add(AuthValidationError.EmptyUsername)
            }
            else if (username.length < 3){
                errors.add(AuthValidationError.ShortUsername)
            }
            else if (username.length > 15){
                errors.add(AuthValidationError.LongUsername)
            }
            return if (errors.isEmpty()) AuthValidationResult.Success else AuthValidationResult.Failure(errors)
        }

        fun validateSignInFields(emailOrUsername: String, password: String): AuthValidationResult{
            val errors = mutableListOf<AuthValidationError>()
            if (TextUtils.isEmpty(emailOrUsername)){
                errors.add(AuthValidationError.EmptyEmailOrUsername)
            }
            if (password.length<5){
                errors.add(AuthValidationError.ShortPassword)
            }
            return if (errors.isEmpty()) AuthValidationResult.Success else AuthValidationResult.Failure(errors)
        }
    }



}