package com.locaspes.data.feed

import com.google.firebase.firestore.DocumentSnapshot
import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getLastDocument(projects: List<ProjectCard>, documents: List<DocumentSnapshot>): DocumentSnapshot?

    fun getAllProjects(): Flow<List<ProjectCard>>

    fun getProjectsPaginated(lastDocument: DocumentSnapshot?): Flow<List<ProjectCard>>
}