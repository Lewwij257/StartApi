package com.locaspes.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(private val userDataStore: UserDataStore) {

    suspend fun saveUserId(userId: String){
        userDataStore.saveUserId(userId)
    }

    suspend fun clearUserId(){
        userDataStore.clearUserId()
    }

    fun getUserId(): Flow<String?>{
        return userDataStore.userId
    }
}