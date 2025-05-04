package com.locaspes.core

import android.net.Uri

interface ImageStorageRepository {
    suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String>
    suspend fun getProfileImageURL(userId: String): Result<String>
}