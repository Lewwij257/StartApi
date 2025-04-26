package com.locaspes.data.registration

import com.locaspes.data.model.UserProfile

interface RegistrationRepository {
    suspend fun signUp(userProfile: UserProfile): Result<String>
    suspend fun signIn(emailOrUsername: String, password: String): Result<String>
}