package com.locaspes.settings

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.core.ImageStorageRepository
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.UserProfile
import com.locaspes.data.user.FirebaseUserActionsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository,
    private val imageStorageRepository: ImageStorageRepository,
    private val userActionsRepository: FirebaseUserActionsRepository
) {

    private val firestore = Firebase.firestore

    suspend fun logOut() {
        userDataRepository.clearUserProfile()
    }

    suspend fun loadUserProfile(userId: String): Result<UserProfile> {
        return firebaseUserActionsRepository.getUserProfile(userId)
    }

    suspend fun uploadAvatar(uri: Uri): Result<String> {
        val userId = userDataRepository.getUserProfile().first()!!.id
        return imageStorageRepository.uploadProfileImage(userId, uri).also { result ->
            result.onSuccess { url ->
                try {
                    firestore.collection("Users").document(userId)
                        .update("avatarURL", url)
                        .await()
                } catch (e: Exception) {
                    return Result.failure(Exception("Ошибка сохранения URL: ${e.message}"))
                }
            }
        }
    }

    suspend fun saveUserProfile(userProfile: UserProfile): Result<String>{
        Log.d("saveprofilelog", "usecase")
        return firebaseUserActionsRepository.saveUserProfile(userProfile)
    }
}