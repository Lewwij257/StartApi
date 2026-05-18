package com.example.project_edit

import com.locaspes.model.ProjectCard
import com.locaspes.model.ProjectIcon
import kotlinx.coroutines.flow.StateFlow

interface IProjectEditViewModel {
    val uiState: StateFlow<ProjectEditUiState>
    fun setOnNavigateBack(onNavigateBack: (()->Unit))
    fun updateProjectTitle(title: String)
    fun updateProjectShortDescription(shortDescription: String)
    fun updateProjectLongDescription(longDescription: String)
    fun updateProjectLookingFor(lookingFor: String)
    fun updateProjectRequiredSkills(requiredSkills: String)
    fun updateProjectTechnologies(technologies: String)
    fun updateSelectedIcon(projectIcon: ProjectIcon)
    fun acceptUsersApplication(projectId: String, userId: String)
    fun declineUsersApplication(projectId: String, userId: String)
    fun getProjectRelatedUsers(projectId: String)
    fun saveEditedProject()

    fun unfollowUser(projectId: String, userId: String)

    fun loadProjectToEdit(projectCard: ProjectCard)

    fun deleteProject(projectId: String)

}