package com.locaspes.data

interface AuthRepository {
    suspend fun signUp(email: String, username: String, password: String): Boolean
    suspend fun signIn(emailOrUsername: String, password: String): Boolean
}