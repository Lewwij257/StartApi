package com.locaspes.data.user

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.ChatItem
import com.locaspes.data.model.Message
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserActionsRepository @Inject constructor(
    private val userDataRepository: UserDataRepository
): UserActionsRepository {

    private val dataBase = Firebase.firestore

    override suspend fun sendMessage(message: Message
    ): Result<String> {
        return try {
            dataBase.collection("Chats")
                .document(message.projectId)
                .collection("Messages")
                .add(message)
                .await()
            Log.d("FirebaseUserActionsRepository", "message sent!")
            Result.success("Успешно!")

        }
        catch (e: Exception){
            Log.d("FirebaseUserActionsRepository", "message not sent!")
            Result.failure(Exception("Ошибка: ${e.message}" ))
        }
    }

    override suspend fun getChatMessages(projectId: String): Flow<Result<List<Message>>> = callbackFlow {
        val listener = dataBase.collection("Chats")
            .document(projectId)
            .collection("Messages")
            .orderBy("date", Query.Direction.ASCENDING)
            .limit(50) // Ограничиваем 50 последними сообщениями
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Result.failure(Exception("Ошибка загрузки сообщений: ${error.message}")))
                    return@addSnapshotListener
                }

                snapshot?.let {
                    val messages = it.documents.mapNotNull { doc ->
                        doc.toObject(Message::class.java)?.apply { id = doc.id }
                    }
                    trySend(Result.success(messages))
                }
            }

        awaitClose { listener.remove() }
    }

    override suspend fun addApplicationToProject(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserProfile().first()!!.id
        try {
            Log.d("Debug","userid" + currentUserId + "projectId" + projectId)
            if (!checkUserAppliedToProject(projectId)) {
                dataBase.collection("Users").document(currentUserId)
                    .update("projectsApplications", FieldValue.arrayUnion(projectId)).await()
                dataBase.collection("Projects").document(projectId)
                    .update("usersApplied", FieldValue.arrayUnion(userDataRepository.getUserProfile().first()!!.id)).await()
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun checkUserAppliedToProject(projectId: String): Boolean{
        val currentUserId = userDataRepository.getUserProfile().first()!!.id
        val userDocument = dataBase.collection("Users").document(currentUserId).get().await()
        val userApplications = userDocument.get("projectsApplications") as? List<String> ?: emptyList()
        Log.d("FirebaseUserActionsRepository", "contains project - ${userApplications.contains(projectId)} ")
        return userApplications.contains(projectId)
    }

    override suspend fun cancelUserApplication(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserProfile().first()!!.id
        try {
            if (checkUserAppliedToProject(projectId)){
                dataBase.collection("Users").document(currentUserId)
                    .update("projectsApplications", FieldValue.arrayRemove(projectId)).await()
                dataBase.collection("Projects").document(projectId)
                    .update("usersApplied", FieldValue.arrayRemove(currentUserId)).await()
            }
            return true
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun createProject(projectCard: ProjectCard): Boolean{
        try {
            projectCard.author = userDataRepository.getUserProfile().first()!!.id
            projectCard.createDate = Timestamp.now()
            val newProjectDocument = dataBase.collection("Projects").add(projectCard).await()
            newProjectDocument.update("id", newProjectDocument.id)
            dataBase.collection("Users")
                .document(userDataRepository.getUserProfile().first()!!.id)
                .update("projectsCreated", FieldValue.arrayUnion(newProjectDocument.id)).await()


            //TODO: ДОДДЕЛАТЬ

            val chatItem = ChatItem(
                projectName = projectCard.name,
                id = newProjectDocument.id,
                projectIconUrl = "",
                lastMessage = "Ещё нет сообщений",
                lastMessageDate = Timestamp.now().toString(),
                hasNewMessages = false
            )

            dataBase.collection("Chats").document(newProjectDocument.id).set(chatItem).await()
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
                "projectsAccepted", FieldValue.arrayUnion(projectId),
                "projectsApplications", FieldValue.arrayRemove(projectId)
            )
            dataBase.collection("Projects").document(projectId).update(
                "usersAccepted", FieldValue.arrayUnion(userId),
                "usersApplied", FieldValue.arrayRemove(userId)
            )
            Result.success("Пользователь успешно принят!")
        }
        catch (e: Exception){
            Result.failure(Exception("Не удалось добавить пользователя!"))
        }
    }

    override suspend fun declineUserApplicationToProject(
        projectId: String, userId: String): Result<String> {
        return try {
            dataBase.collection("Users").document(userId).update(
                "projectsApplications", FieldValue.arrayRemove(projectId)
            )
            dataBase.collection("Projects").document(projectId).update(
                "usersApplied", FieldValue.arrayRemove(userId)
            )
            Result.success("Пользователь успешно откланён!")
        }
        catch (e: Exception){
            Result.failure(Exception("Не удалось откланить пользователя!"))
        }

    }

    override suspend fun unfollowProject(projectId: String): Result<String> {
        return try {
            dataBase.collection("Users").document(userDataRepository.getUserProfile().first()!!.id).update(
                "projectsAccepted", FieldValue.arrayRemove(projectId)
            )
            dataBase.collection("Projects").document(projectId).update(
                "usersAccepted", FieldValue.arrayRemove(userDataRepository.getUserProfile().first()!!.id)
            )
            Result.success("Успешно отписан!")
        }
        catch (e: Exception){
            Result.failure(Exception("Не удалось отписаться!"))
        }
    }

    override suspend fun getUserChats(): Result<List<ChatItem>> {

        Log.d("FirebaseUserActionsRepository", "getUserChats")

        return try {

            val userDocumentSnapshot = dataBase.collection("Users")
                .document(userDataRepository.getUserProfile().first()!!.id).get().await()

            val projectsCreated = userDocumentSnapshot.get("projectsCreated") as? List<String> ?: emptyList()
            val projectsAccepted = userDocumentSnapshot.get("projectsAccepted") as? List<String> ?: emptyList()
            val allProjectIds = (projectsCreated + projectsAccepted).distinct()

            Log.d("FirebaseUserActionsRepository", allProjectIds.toString())

            if (allProjectIds.isEmpty()){
                Result.success(emptyList<ChatItem>())
            }

            val chatItemsList = mutableListOf<ChatItem>()
            for (projectId in allProjectIds){
                val chatDocument = dataBase
                    .collection("Chats")
                    .document(projectId)
                    .get()
                    .await()
                if (chatDocument.exists()){
                    val chatItem = chatDocument.toObject(ChatItem::class.java)
                    chatItemsList.add(chatItem!!)
                }
            }
            Log.d("FirebaseUserActionsRepository", "getUserChats success ${chatItemsList.size}")
            Result.success(chatItemsList)
        }
        catch (e: Exception){
            Result.failure(Exception("ошибка: ${e.message}"))
        }


    }

    override suspend fun checkUserAcceptedToProject(projectId: String): Boolean {
        val currentUserId = userDataRepository.getUserProfile().first()!!.id
        val userDocument = dataBase.collection("Users").document(currentUserId).get().await()
        val projectsAccepted = userDocument.get("projectsAccepted") as? List<String> ?: emptyList()
        Log.d("FirebaseUserActionsRepository", "contains project - ${projectsAccepted.contains(projectId)} ")
        return projectsAccepted.contains(projectId)
    }


}