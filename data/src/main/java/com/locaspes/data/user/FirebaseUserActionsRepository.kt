package com.locaspes.data.user

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserActionsRepository @Inject constructor(
    private val userDataRepository: UserDataRepository
): UserActionsRepository {

    private val dataBase = Firebase.firestore

    override suspend fun addApplicationToProject(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserId().first()!!
        try {
            Log.d("Debug","userid" + currentUserId + "projectId" + projectId)
            if (!checkUserAppliedToProject(projectId)) {
                dataBase.collection("Users").document(currentUserId)
                    .update("projectsApplications", FieldValue.arrayUnion(projectId)).await()
                dataBase.collection("Projects").document(projectId)
                    .update("usersApplied", FieldValue.arrayUnion(userDataRepository.getUserId().first()!!)).await()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun checkUserAppliedToProject(projectId: String): Boolean{
        val currentUserId = userDataRepository.getUserId().first()!!
        val userDocument = dataBase.collection("Users").document(currentUserId).get().await()
        val userApplications = userDocument.get("projectsApplications") as? List<String> ?: emptyList()
        Log.d("FirebaseUserActionsRepository", "contains project - ${userApplications.contains(projectId)} ")
        return userApplications.contains(projectId)
    }

    override suspend fun cancelUserApplication(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserId().first()!!
        try {
            if (checkUserAppliedToProject(projectId)){
                dataBase.collection("Users").document(currentUserId)
                    .update("projectsApplications", FieldValue.arrayRemove(projectId)).await()
            }
            return true
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun createProject(projectCard: ProjectCard): Boolean{
        try {
            projectCard.author = userDataRepository.getUserId().first()!!
            projectCard.createDate = Timestamp.now()
            val newProjectDocument = dataBase.collection("Projects").add(projectCard).await()
            newProjectDocument.update("id", newProjectDocument.id)
            dataBase.collection("Users")
                .document(userDataRepository.getUserId().first()!!)
                .update("projectsCreated", FieldValue.arrayUnion(newProjectDocument.id))
            return true
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun getUserProfile(userId: String): Result<UserProfile> {
        return try {
            val snapshot = dataBase.collection("Users").document(userId).get().await()
            if (snapshot.exists()){
                Result.success(snapshot.toObject(UserProfile::class.java) ?: UserProfile())
            }
            else{
                Result.failure(IllegalStateException("Документ пользователя с ID $userId не найден"))
            }
        }
        catch (e: Exception){
            Result.failure(FirebaseException("Ошибка при загрузке профиля: ${e.message}", e))
        }
    }
    override suspend fun saveUserProfile(userProfile: UserProfile): Result<String> {
        return try {
            // Логируем входные данные
            Log.d("saveprofilelog", "Начало сохранения профиля: id=${userProfile.id}, " +
                    "username=${userProfile.username}, avatarURL=${userProfile.avatarURL}, " +
                    "description=${userProfile.profileDescription}, skills=${userProfile.skills}")

            // Проверяем, не пустой ли ID
            if (userProfile.id.isBlank()) {
                Log.e("saveprofilelog", "Ошибка: userProfile.id пустой")
                return Result.failure(Exception("ID пользователя не может быть пустым"))
            }

            // Проверяем аутентификацию
            val currentUser = dataBase.collection("Users").document(userProfile.id).get().await()
            if (currentUser == null) {
                Log.e("saveprofilelog", "Ошибка: пользователь не аутентифицирован")
                return Result.failure(Exception("Пользователь не аутентифицирован"))
            } else {
                Log.d("saveprofilelog", "Текущий пользователь: uid=${currentUser.id}")
            }

            // Выполняем обновление
            dataBase.collection("Users").document(userProfile.id).update(
                mapOf(
                    "username" to userProfile.username,
                    "avatarURL" to userProfile.avatarURL,
                    "profileDescription" to userProfile.profileDescription,
                    "skills" to userProfile.skills,
                    "profession" to userProfile.profession
                )
            ).await()

            Log.d("saveprofilelog", "FirebaseSuccess: профиль успешно обновлён")
            Result.success("Данные успешно изменены!")
        } catch (e: Exception) {
            Log.e("saveprofilelog", "FirebaseFailure: ${e.message}", e)
            Result.failure(Exception("Ошибка: ${e.message}"))
        }
    }

    override suspend fun acceptUserApplicationToProject(
        projectId: String, userId: String): Result<String> {
        return try {
            dataBase.collection("Users").document(userId).update(
                "projectsAccepted", projectId
            )
            dataBase.collection("Projects").document(projectId).update(
                "usersAccepted", userId
            )
            Result.success("Пользователь успешно принят!")
        }
        catch (e: Exception){
            Result.failure(Exception("Не удалось добавить пользователя!"))
        }

    }

}