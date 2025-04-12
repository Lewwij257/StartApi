package com.locaspes.settings

import com.locaspes.data.UserDataRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository) {

    suspend fun logOut(){
        userDataRepository.clearUserId()
    }

}