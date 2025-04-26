package com.locaspes.projects

import com.locaspes.data.UserDataRepository
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import com.locaspes.data.user.FirebaseUserActionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProjectsUseCase @Inject constructor(
    private val firebaseFeedRepository: FirebaseFeedRepository,
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository,
    private val userDataRepository: UserDataRepository
) {

    suspend fun loadUserRelatedProjects(): Flow<List<List<ProjectCard>>> {
        return firebaseFeedRepository.getUserRelatedProjects()
    }
    suspend fun checkIfUserAppliedToProject(projectId: String): Boolean{
        return firebaseUserActionsRepository.checkUserAppliedToProject(projectId)
    }

    suspend fun applyUserToProject(projectId: String): Boolean{
        return firebaseUserActionsRepository.addApplicationToProject(projectId)
    }

    suspend fun cancelUserApplication(projectId: String): Boolean{
        return firebaseUserActionsRepository.cancelUserApplication(projectId)
    }

    suspend fun createProject(projectCard: ProjectCard): Boolean{
        return firebaseUserActionsRepository.createProject(projectCard)
    }

    suspend fun getProjectRelatedUsers(projectId: String): Result<List<List<UserProfile>>>{
        return firebaseFeedRepository.getProjectRelatedUsers(projectId)
    }
}