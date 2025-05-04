package com.locaspes.data.user

import com.google.firebase.firestore.auth.User
import com.locaspes.data.model.ChatItem
import com.locaspes.data.model.Message
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserActionsRepository {
    suspend fun addApplicationToProject(projectId: String): Boolean
    suspend fun checkUserAppliedToProject(projectId: String): Boolean
    suspend fun cancelUserApplication(projectId: String): Boolean
    suspend fun createProject(projectCard: ProjectCard): Boolean
    suspend fun getUserProfile(userId: String): Result<UserProfile>
    suspend fun saveUserProfile(userProfile: UserProfile): Result<String>
    suspend fun acceptUserApplicationToProject(projectId: String, userId: String): Result<String>
    suspend fun declineUserApplicationToProject(projectId: String, userId: String): Result<String>
    suspend fun unfollowProject(projectId: String): Result<String>
    suspend fun sendMessage(message: Message): Result<String>
    suspend fun getChatMessages(projectId: String): Flow<Result<List<Message>>>
    suspend fun getUserChats(): Result<List<ChatItem>>
    suspend fun checkUserAcceptedToProject(projectId: String): Boolean
}