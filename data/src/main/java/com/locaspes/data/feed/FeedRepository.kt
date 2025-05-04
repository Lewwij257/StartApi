package com.locaspes.data.feed

import com.google.firebase.firestore.DocumentSnapshot
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getLastDocument(projects: List<ProjectCard>, documents: List<DocumentSnapshot>): DocumentSnapshot?

    fun getAllProjects(): Flow<List<ProjectCard>>

    fun getProjectsPaginated(lastDocument: DocumentSnapshot?): Flow<List<ProjectCard>>

    fun getUserRelatedProjects(): Flow<List<List<ProjectCard>>>

    suspend fun getProjectRelatedUsers(projectId: String): Result<List<List<UserProfile>>>
}