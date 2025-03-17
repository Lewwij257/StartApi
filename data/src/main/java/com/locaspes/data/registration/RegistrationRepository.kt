package com.locaspes.data.registration

interface RegistrationRepository {
    suspend fun signUp(email: String, username: String, password: String): Boolean
    suspend fun signIn(emailOrUsername: String, password: String): Boolean
}