package com.locaspes.startapi

import com.locaspes.data.model.UserProfile
import com.locaspes.data.registration.FirebaseRegistrationRepository
import com.locaspes.utils.AuthInputValidator
import com.locaspes.utils.AuthValidationResult
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseRegistrationRepository) {

    suspend fun signUp(userProfile: UserProfile): Result<String>{

//        if (!AuthInputValidator.validateSignUpFields(
//            email = userProfile.email,
//            username = userProfile.username,
//            password = userProfile.password
//        )){
//            return Result.failure(Exception("Некоторые поля ввода некорректны"))
//        }
//        return firebaseAuthRepository.signUp(userProfile)

        when (val authValidationResult = AuthInputValidator.validateSignUpFields(
            email = userProfile.email,
            username = userProfile.username,
            password = userProfile.password
        )) {
            is AuthValidationResult.Success -> {
                return firebaseAuthRepository.signUp(userProfile)
            }
            is AuthValidationResult.Failure -> {
                return Result.failure(Exception(authValidationResult.errors.joinToString{it.toString()}))
            }
        }

    }
}