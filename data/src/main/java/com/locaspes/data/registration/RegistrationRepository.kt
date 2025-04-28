package com.locaspes.data.registration

import com.locaspes.data.model.AuthResult
import com.locaspes.data.model.UserProfile

interface RegistrationRepository {
    suspend fun signUp(userProfile: UserProfile): AuthResult
    suspend fun signIn(emailOrUsername: String, password: String): Result<String>
}