package com.locaspes.domain.repositories

import com.locaspes.domain.entities.SignUpData

interface SignUpRepository {

    suspend fun signUp(newAccountData: SignUpData): Boolean

    fun getCollection():  MutableList<Map<String, Any>>

}