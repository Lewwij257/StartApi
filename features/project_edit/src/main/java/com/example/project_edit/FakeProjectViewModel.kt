package com.example.project_edit

import com.locaspes.model.ProjectCard
import com.locaspes.model.ProjectIcon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeProjectViewModel() : IProjectEditViewModel {

    override val uiState: StateFlow<ProjectEditUiState> = MutableStateFlow(ProjectEditUiState())
    override fun setOnNavigateBack(onNavigateBack: () -> Unit) {

    }

    override fun updateProjectTitle(title: String) {
    }

    override fun updateProjectShortDescription(shortDescription: String) {
    }

    override fun updateProjectLongDescription(longDescription: String) {
    }

    override fun updateProjectLookingFor(lookingFor: String) {
    }

    override fun updateProjectRequiredSkills(requiredSkills: String) {
    }

    override fun updateProjectTechnologies(technologies: String) {
    }

    override fun updateSelectedIcon(projectIcon: ProjectIcon) {
    }

    override fun acceptUsersApplication(projectId: String, userId: String) {
    }

    override fun declineUsersApplication(projectId: String, userId: String) {
    }

    override fun getProjectRelatedUsers(projectId: String) {
    }

    override fun saveEditedProject() {
    }

    override fun unfollowUser(projectId: String, userId: String) {
        
    }

    override fun loadProjectToEdit(projectCard: ProjectCard) {
    }

    override fun deleteProject(projectId: String) {
        TODO("Not yet implemented")
    }
}