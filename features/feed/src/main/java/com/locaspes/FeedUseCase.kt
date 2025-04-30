package com.locaspes

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.locaspes.data.UserDataRepository
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import com.locaspes.data.user.FirebaseUserActionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FeedUseCase @Inject constructor(
    private val firebaseFeedRepository: FirebaseFeedRepository,
    private val firebaseUserActionsRepository: FirebaseUserActionsRepository,
    private val userDataRepository: UserDataRepository) {

    private var lastDocument: DocumentSnapshot? = null
    private var hasMoreData: Boolean = true

    fun loadPaginatedProjects(): Flow<List<ProjectCard>> = firebaseFeedRepository.getProjectsPaginated(lastDocument)

    fun hasMoreData(): Boolean = hasMoreData

    suspend fun getUserProfile(): UserProfile{
        return userDataRepository.getUserProfile().first()!!
    }

    suspend fun unfollowProject(projectId: String): Result<String>{
        return firebaseUserActionsRepository.unfollowProject(projectId)
    }

    suspend fun updatePaginationState(newProjects: List<ProjectCard>){
        if (newProjects.isEmpty()){
            hasMoreData = false
            return
        }
        val documents = firebaseFeedRepository.getDocuments(lastDocument)
        lastDocument = documents.lastOrNull()

        hasMoreData = newProjects.size == 10
    }

    fun resetPagination(){
        lastDocument = null
        hasMoreData = true
    }

    suspend fun checkIfUserAppliedToProject(currentUserId: String, projectId: String): Boolean{
        return firebaseUserActionsRepository.checkUserAppliedToProject(projectId)
    }

    suspend fun applyUserToProject(currentUserId: String, projectId: String): Boolean{
        return firebaseUserActionsRepository.addApplicationToProject(projectId)
    }

    suspend fun cancelUserApplication(projectId: String): Boolean{
        return firebaseUserActionsRepository.cancelUserApplication(projectId)
    }

    suspend fun getProjectRelatedUsers(projectId: String): Result<List<List<UserProfile>>>{
        return firebaseFeedRepository.getProjectRelatedUsers(projectId)
    }

}