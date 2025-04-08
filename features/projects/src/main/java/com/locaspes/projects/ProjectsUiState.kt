package com.locaspes.projects

import com.locaspes.data.model.ProjectCard

data class ProjectsUiState(
    val userRelatedProjects: List<ProjectCard> = emptyList()
)