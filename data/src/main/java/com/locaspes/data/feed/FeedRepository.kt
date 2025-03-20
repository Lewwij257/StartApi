package com.locaspes.data.feed

import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getAllProjects(): Flow<List<ProjectCard>>

}