package com.locaspes

import com.locaspes.data.registration.FirebaseRegistrationRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val firebaseRegistrationRepository: FirebaseRegistrationRepository
) {
    suspend fun signIn(emailOrUsername: String, password: String): Result<String>{
        return firebaseRegistrationRepository.signIn(emailOrUsername, password)
    }
}