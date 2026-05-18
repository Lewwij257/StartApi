package com.locaspes.projects

import com.locaspes.model.ProjectCard
import com.locaspes.model.ProjectIcon
import kotlinx.coroutines.flow.StateFlow

interface IProjectsViewModel {
    val uiState: StateFlow<ProjectsUiState>

    fun saveEditedProject()
    fun loadUserRelatedProjects()
    fun acceptUsersApplication(projectId: String, userId: String)
    fun declineUsersApplication(projectId: String, userId: String)
    fun changeCanApplyState(projectId: String)
    fun applyUserToProject(projectId: String)
    fun cancelUserApplication(projectId: String)
    fun createProject()
    fun getProjectRelatedUsers(projectId: String)

    fun updateCreateProjectTitle(title: String)
    fun updateCreateProjectShortDescription(shortDescription: String)
    fun updateCreateProjectLongDescription(longDescription: String)
    fun updateCreateProjectLookingFor(lookingFor: String)
    fun updateCreateProjectRequiredSkills(requiredSkills: String)
    fun updateCreateProjectTechnologies(technologies: String)

    fun updateProjectForEdit(project: ProjectCard)
    fun updateEditProjectId(projectId: String)
    fun updateEditProjectTitle(title: String)
    fun updateEditProjectShortDescription(shortDescription: String)
    fun updateEditProjectLongDescription(longDescription: String)
    fun updateEditProjectLookingFor(lookingFor: String)
    fun updateEditProjectRequiredSkills(requiredSkills: String)
    fun updateEditProjectTechnologies(technologies: String)
    fun updateEditProjectApplies(applies: List<String>)
    fun updateEditProjectAccepted(accepted: List<String>)
    fun updateEditProjectCreator(creator: String)

    fun updateSelectedProject(project: ProjectCard)

    fun updateSelectedIcon(projectIcon: ProjectIcon)
}