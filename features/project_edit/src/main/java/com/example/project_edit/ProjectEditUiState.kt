package com.example.project_edit

import com.locaspes.model.ProjectIcon
import com.locaspes.model.UserProfile

data class ProjectEditUiState (
    val projectId: String = "",
    val projectTitle: String = "",
    val projectShortDescription: String = "",
    val projectLongDescription: String = "",
    val projectLookingFor: String = "",
    val projectRequiredSkills: String = "",
    val projectTechnologies: String = "",
    val projectSelectedIcon: ProjectIcon = ProjectIcon.Default,

    val projectRelatedUsers: List<List<UserProfile>> = emptyList(),

    val successDeleteProject: Boolean? = null
)