package com.locaspes.projects

import com.locaspes.data.model.ProjectCard

data class ProjectsUiState(
    val userRelatedProjects: List<List<ProjectCard>> = emptyList(),
    val canApply: Boolean? = null,
    val createProjectTitle: String = "",
    val createProjectShortDescription: String = "",
    val createProjectLongDescription: String = "",
    val createProjectLookingFor: String = "",
    val createProjectRequiredSkills: String = "",
    val createProjectTechnologies: String = "",
    val successCreatingProject: Boolean? = null
)