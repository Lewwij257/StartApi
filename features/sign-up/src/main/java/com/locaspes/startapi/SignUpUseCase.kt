package com.locaspes.startapi

import com.locaspes.data.registration.FirebaseRegistrationRepository
import javax.inject.Inject

//class SignUpUseCase @Inject constructor(private val firebaseAuthRepository: FirebaseRegistrationRepository) {
//    //authRepository.signUp(email, username, password)
//    suspend operator fun invoke(
//        email: String,
//        username: String,
//        password: String
//    ): Boolean {
//        return firebaseAuthRepository.signUp(email, username, password)
//    }
//}
class SignUpUseCase @Inject constructor(private val firebaseAuthRepository: FirebaseRegistrationRepository) {
    //authRepository.signUp(email, username, password)
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): Boolean {
        return firebaseAuthRepository.signUp(email, username, password)
    }
}