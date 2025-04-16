package com.locaspes.settings

import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.UserProfile
import com.locaspes.data.user.FirebaseUserActionsRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository) {

    suspend fun logOut(){
        userDataRepository.clearUserId()
    }

    suspend fun loadUserProfile(userId: String): Result<UserProfile>{
        return firebaseUserActionsRepository.getUserProfile(userId)
    }

}