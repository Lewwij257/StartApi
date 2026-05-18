package com.example.project_edit

import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.user.FirebaseUserActionsRepository
import com.locaspes.data.user.UserActionsRepository
import com.locaspes.model.ProjectCard
import com.locaspes.model.UserProfile
import javax.inject.Inject

class ProjectEditUseCase @Inject constructor(
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository,
    private val firebaseFeedRepository: FirebaseFeedRepository) {
    suspend fun acceptUserApplication(projectId: String, userId: String): Result<String> {
        return firebaseUserActionsRepository.acceptUserApplicationToProject(projectId, userId)
    }
    suspend fun declineUserApplication(projectId: String, userId: String): Result<String>{
        return firebaseUserActionsRepository.declineUserApplicationToProject(projectId, userId)
    }
    suspend fun saveEditedProject(projectCard: ProjectCard): Result<String>{
        return firebaseUserActionsRepository.saveEditedProject(projectCard)
    }
    suspend fun getProjectRelatedUsers(projectId: String): Result<List<List<UserProfile>>>{
        return firebaseFeedRepository.getProjectRelatedUsers(projectId)
    }

    suspend fun unFollowUser(projectId: String, userId: String): Result<String>{
        return firebaseUserActionsRepository.unFollowUserFromProject(userId, projectId)
    }

    suspend fun deleteProject(projectId: String): Result<String>{
        return firebaseUserActionsRepository.deleteProject(projectId)
    }
}