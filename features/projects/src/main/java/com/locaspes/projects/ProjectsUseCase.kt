package com.locaspes.projects

import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProjectsUseCase @Inject constructor(private val firebaseFeedRepository: FirebaseFeedRepository) {

    fun loadUserRelatedProjects(): Flow<List<ProjectCard>> {
        return firebaseFeedRepository.getUserRelatedProjects()
    }

}