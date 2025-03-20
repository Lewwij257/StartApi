package com.locaspes

import com.google.firebase.firestore.DocumentSnapshot
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedUseCase @Inject constructor(private val firebaseFeedRepository: FirebaseFeedRepository) {

    private var lastDocument: DocumentSnapshot? = null
    private var hasMoreData: Boolean = true

    fun loadPaginatedProjects(): Flow<List<ProjectCard>> = firebaseFeedRepository.getProjectsPaginated(lastDocument)

    fun hasMoreData(): Boolean = hasMoreData

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

}