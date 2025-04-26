package com.locaspes.projects

import com.locaspes.data.model.ProjectCard
import com.locaspes.data.model.UserProfile

data class ProjectsUiState(
    val userRelatedProjects: List<List<ProjectCard>> = emptyList(),

    val projectRelatedUsers: List<List<UserProfile>> = emptyList(),
    val selectedProject: ProjectCard = ProjectCard(),

    val canApply: Boolean? = null,
    val createProjectTitle: String = "",
    val createProjectShortDescription: String = "",
    val createProjectLongDescription: String = "",
    val createProjectLookingFor: String = "",
    val createProjectRequiredSkills: String = "",
    val createProjectTechnologies: String = "",
    val successCreatingProject: Boolean? = null,

    val editProjectTitle: String = "",
    val editProjectShortDescription: String = "",
    val editProjectLongDescription: String = "",
    val editProjectLookingFor: String = "",
    val editProjectRequiredSkills: String = "",
    val editProjectTechnologies: String = "",
    //люди которые подписались
    val editProjectApplies: List<String> = emptyList(),
    val editProjectAccepted: List<String> = emptyList(),
    val editProjectCreator: String = ""
    )