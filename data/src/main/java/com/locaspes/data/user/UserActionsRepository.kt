package com.locaspes.data.user

import com.locaspes.data.model.ProjectCard

interface UserActionsRepository {
    suspend fun addApplicationToProject(projectId: String): Boolean
    suspend fun checkUserAppliedToProject(projectId: String): Boolean
    suspend fun cancelUserApplication(projectId: String): Boolean
    suspend fun createProject(projectCard: ProjectCard): Boolean
}