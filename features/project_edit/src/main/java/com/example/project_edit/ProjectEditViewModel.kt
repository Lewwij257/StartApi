package com.example.project_edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.model.ProjectCard
import com.locaspes.model.ProjectIcon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectEditViewModel @Inject constructor(
    private val projectEditUseCase: ProjectEditUseCase) : ViewModel(), IProjectEditViewModel {

    private val _uiState = MutableStateFlow(ProjectEditUiState())
    override val uiState = _uiState as StateFlow<ProjectEditUiState>

    var onNavigateToProjectsScreen: (() -> Unit)? = null

    override fun setOnNavigateBack(onNavigateBack: (()->Unit)){
        onNavigateToProjectsScreen = onNavigateBack
    }

    override fun loadProjectToEdit(projectCard: ProjectCard){
        viewModelScope.launch {
            val projectRelatedUsersResult = projectEditUseCase.getProjectRelatedUsers(projectCard.id)
            if (projectRelatedUsersResult.isSuccess){
                _uiState.update { it.copy(projectRelatedUsers = projectRelatedUsersResult.getOrNull()!!) }
            }
            else{
                //TODO!!!!
            }
        }
        _uiState.update { it.copy(
            projectId = projectCard.id,
            projectTitle = projectCard.name,
            projectShortDescription = projectCard.shortDescription,
            projectLongDescription = projectCard.longDescription,
            projectLookingFor = projectCard.lookingFor.toString(),
            projectRequiredSkills = projectCard.requiredSkills.toString(),
            projectTechnologies = projectCard.technologies.toString(),
            projectSelectedIcon = projectCard.projectIcon
        ) }
    }

    override fun updateProjectTitle(title: String) {
        _uiState.update { it.copy(projectTitle = title) }
    }

    override fun updateProjectShortDescription(shortDescription: String) {
        _uiState.update { it.copy(projectShortDescription = shortDescription) }
    }

    override fun updateProjectLongDescription(longDescription: String) {
        _uiState.update { it.copy(projectLongDescription = longDescription) }
    }

    override fun updateProjectLookingFor(lookingFor: String) {
        _uiState.update { it.copy(projectLookingFor = lookingFor) }
    }

    override fun updateProjectRequiredSkills(requiredSkills: String) {
        _uiState.update { it.copy(projectRequiredSkills = requiredSkills) }
    }

    override fun updateProjectTechnologies(technologies: String) {
        _uiState.update { it.copy(projectTechnologies = technologies) }
    }

    override fun updateSelectedIcon(projectIcon: ProjectIcon) {
        _uiState.update { it.copy(projectSelectedIcon = projectIcon) }
    }

    override fun acceptUsersApplication(projectId: String, userId: String){
        viewModelScope.launch {
            projectEditUseCase.acceptUserApplication(projectId, userId)
            Log.d("ProjectEditModule", "acceptUsersApplication: projectId: $projectId, userId: $userId")
        }
    }

    override fun declineUsersApplication(projectId: String, userId: String) {
        viewModelScope.launch {
            projectEditUseCase.declineUserApplication(projectId, userId)

        }
    }
    override fun getProjectRelatedUsers(projectId: String) {
        viewModelScope.launch {
            val projectRelatedUsersResult = projectEditUseCase.getProjectRelatedUsers(projectId)
            if (projectRelatedUsersResult.isSuccess){
                _uiState.update { it.copy(projectRelatedUsers = projectRelatedUsersResult.getOrNull()!!) }
            }
        }
    }

    override fun saveEditedProject(){
        viewModelScope.launch {
            val saveEditProjectResult = projectEditUseCase.saveEditedProject(
                ProjectCard(
                    id = uiState.value.projectId,
                    name = uiState.value.projectTitle,
                    shortDescription = uiState.value.projectShortDescription,
                    longDescription = uiState.value.projectLongDescription,
                    lookingFor = uiState.value.projectLookingFor.split(", ", " "),
                    requiredSkills = uiState.value.projectRequiredSkills.split(", ", " "),
                    technologies = uiState.value.projectTechnologies.split(", ", " "),
                    projectIcon = uiState.value.projectSelectedIcon
                )
            )
            if (saveEditProjectResult.isSuccess){
                _uiState.update { it.copy() }
            }
        }
    }

    override fun unfollowUser(projectId: String, userId: String) {
        viewModelScope.launch {
            val result = projectEditUseCase.unFollowUser(projectId, userId)
            if (result.isSuccess){
                Log.d("ProjectEditViewModel", "success!")
            }
            else{
                Log.d("ProjectEditViewModel", result.getOrNull().toString())
            }
        }
    }

    override fun deleteProject(projectId: String) {
        viewModelScope.launch {
            val result = projectEditUseCase.deleteProject(projectId)
            if (result.isSuccess){
                _uiState.update { it.copy(successDeleteProject = true) }
                onNavigateToProjectsScreen!!()
            }
            else{
                _uiState.update { it.copy(successDeleteProject = false) }
            }
        }
    }
}