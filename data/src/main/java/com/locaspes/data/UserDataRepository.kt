package com.locaspes.data

import com.locaspes.model.UserProfile
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

    /**
     * set if app opened for the first time state
     */
    suspend fun setFirstOpenState(state: Boolean){
        userDataStore.editFirstOpenState(state)
    }

    /**
     * returns if app opened for the first time
     */
    fun getFirstOpenState(): Flow<Boolean?>{
        return userDataStore.firstOpenState
    }

}