package com.locaspes.data

import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(private val userDataStore: UserDataStore) {

    suspend fun saveUserProfile(userProfile: UserProfile){
        userDataStore.saveUserProfile(userProfile)
    }

    suspend fun saveUserId(userId: String){
        userDataStore.saveUserId(userId)
    }

    suspend fun saveUserName(userName: String){
        userDataStore.saveUserName(userName)
    }

    suspend fun getUserName(): Flow<String?>{
        return userDataStore.userName
    }

    suspend fun clearUserId(){
        userDataStore.clearUserId()
    }

    suspend fun getUserId(): Flow<String?>{
        return userDataStore.userId
    }




}