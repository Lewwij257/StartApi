package com.locaspes.data

import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(private val userDataStore: UserDataStore) {

    suspend fun saveUserProfile(userProfile: UserProfile){
        userDataStore.saveUserProfile(userProfile)
    }

    suspend fun clearUserProfile(){
        userDataStore.clearUserProfile()
    }

    fun getUserProfile(): Flow<UserProfile?>{
        return userDataStore.userProfile
    }

}