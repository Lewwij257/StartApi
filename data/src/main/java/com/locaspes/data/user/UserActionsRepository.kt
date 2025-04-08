package com.locaspes.data.user

interface UserActionsRepository {
    suspend fun addApplicationToProject(currentUserId: String, projectId: String): Boolean
    suspend fun checkUserAppliedToProject(currentUserId: String, projectId: String): Boolean
}