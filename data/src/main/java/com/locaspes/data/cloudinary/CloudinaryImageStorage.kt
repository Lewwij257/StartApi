package com.locaspes.data.cloudinary

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.core.ImageStorageRepository
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.quality
import id.zelory.compressor.constraint.resolution
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.coroutines.resume

class CloudinaryImageStorage(
    private val context: Context
) : ImageStorageRepository {

    private val database = Firebase.firestore

    override suspend fun uploadProfileImage(userId: String, imageUri: Uri): Result<String> {
        return try {
            if (userId.isBlank()) {
                return Result.failure(IllegalArgumentException("User ID cannot be empty"))
            }
            // Сжатие изображения
            val compressedFile = Compressor.compress(context, imageUri.toFile(context)) {
                resolution(800, 800) // Максимальное разрешение
                quality(80) // Качество 80%
            }
            // Загрузка в Cloudinary
            val url = uploadToCloudinary(userId, compressedFile)
            Result.success(url)
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка загрузки: ${e.message}"))
        }
    }

    override suspend fun getProfileImageURL(userId: String): Result<String> {
        return try {
            if (userId.isBlank()) {
                return Result.failure(IllegalArgumentException("User ID cannot be empty"))
            }
            val document = database.collection("Users").document(userId).get().await()
            if (document.exists()) {
                val profileImageUrl = document.getString("avatarURL")
                if (!profileImageUrl.isNullOrBlank()) {
                    return Result.success(profileImageUrl)
                } else {
                    return Result.failure(Exception("Default avatar"))
                }
            } else {
                return Result.failure(Exception("No user with this id"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("profileImageURL access error: ${e.message}"))
        }
    }

    private suspend fun uploadToCloudinary(userId: String, file: File): String =
        suspendCancellableCoroutine { continuation ->
            MediaManager.get().upload(file.absolutePath) // путь к файлу
                .option("public_id", "users/$userId/profilePicture")
                .callback(object : UploadCallback {
                    override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                        val url = resultData["secure_url"].toString()
                        continuation.resume(url)
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        continuation.resumeWith(Result.failure(Exception(error.description)))
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {}
                    override fun onReschedule(requestId: String, error: ErrorInfo) {}
                    override fun onStart(requestId: String) {}
                }).dispatch()
        }
}

// Вспомогательная функция для преобразования Uri в File
fun Uri.toFile(context: Context): File {
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg") // Уникальное имя
    context.contentResolver.openInputStream(this)?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    } ?: throw IOException("Не удалось открыть InputStream для Uri")
    return file
}