package com.locaspes.startapi

import android.os.Build
import androidx.annotation.RequiresExtension
import com.locaspes.data.model.AuthResult
import com.locaspes.data.model.UserProfile
import com.locaspes.data.registration.FirebaseRegistrationRepository
import com.locaspes.utils.AuthInputValidator
import com.locaspes.utils.AuthValidationResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseRegistrationRepository) {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun signUp(userProfile: UserProfile): AuthResult {
        return when (val authValidationResult = AuthInputValidator.validateSignUpFields(
            email = userProfile.email,
            username = userProfile.username,
            password = userProfile.password
        )) {
            is AuthValidationResult.Success -> {
                firebaseAuthRepository.signUp(userProfile)
            }
            is AuthValidationResult.Failure -> {
                AuthResult.ValidationFailure(authValidationResult)
            }
        }
    }
}