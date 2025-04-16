package com.locaspes.data.user

import com.google.firebase.firestore.auth.User
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile

interface UserActionsRepository {
    suspend fun addApplicationToProject(projectId: String): Boolean
    suspend fun checkUserAppliedToProject(projectId: String): Boolean
    suspend fun cancelUserApplication(projectId: String): Boolean
    suspend fun createProject(projectCard: ProjectCard): Boolean
    suspend fun getUserProfile(userId: String): Result<UserProfile>
}