package com.locaspes.projects

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.locaspes.data.UserDataRepository
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val projectsUseCase: ProjectsUseCase): ViewModel() {

    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    init {
        loadUserRelatedProjects()
    }

    fun loadUserRelatedProjects() {
        viewModelScope.launch {
            val userRelatedProjects = projectsUseCase.loadUserRelatedProjects().collect{ projects ->
                _uiState.update { it.copy(userRelatedProjects = projects) }
            }
        }
    }



    fun changeCanApplyState(projectId: String){
        viewModelScope.launch {
            try{
                _uiState.update { it.copy( canApply = !(projectsUseCase.checkIfUserAppliedToProject(projectId))) }
            }
            catch (e: Exception){
                throw e
            }
        }
    }

    fun applyUserToProject(projectId: String){
        viewModelScope.launch {
            _uiState.update{it.copy(canApply = null)}
            projectsUseCase.applyUserToProject(projectId)
            changeCanApplyState(projectId)
            loadUserRelatedProjects()
        }
    }

    fun cancelUserApplication(projectId: String){
        viewModelScope.launch {
            _uiState.update{it.copy(canApply = null)}
            projectsUseCase.cancelUserApplication(projectId)
            changeCanApplyState(projectId)
            loadUserRelatedProjects()
        }
    }

    fun createProject() {
        viewModelScope.launch {
            val projectCard = ProjectCard(
                id = "",
                name = _uiState.value.createProjectTitle,
                shortDescription = _uiState.value.createProjectShortDescription,
                longDescription = _uiState.value.createProjectLongDescription,
                lookingFor = TextParser.parseString(_uiState.value.createProjectLookingFor),
                requiredSkills = TextParser.parseString(_uiState.value.createProjectRequiredSkills),
                technologies = TextParser.parseString(_uiState.value.createProjectTechnologies),
            )
            val isSuccessCreatingProject = projectsUseCase.createProject(projectCard)
            _uiState.update { it.copy(successCreatingProject = isSuccessCreatingProject) }
            if (isSuccessCreatingProject){
                _uiState.update {
                    it.copy(
                        successCreatingProject = isSuccessCreatingProject,
                        createProjectTitle = "",
                        createProjectShortDescription = "",
                        createProjectLongDescription = "",
                        createProjectLookingFor = "",
                        createProjectRequiredSkills = "",
                        createProjectTechnologies = ""
                    )
                }
            }
            kotlinx.coroutines.delay(2000)
            _uiState.update { it.copy(successCreatingProject = null) }
        }
    }

    fun updateCreateProjectTitle(title: String) {
        _uiState.update { it.copy(createProjectTitle = title) }
    }

    fun updateCreateProjectShortDescription(shortDescription: String) {
        _uiState.update { it.copy(createProjectShortDescription = shortDescription) }
    }

    fun updateCreateProjectLongDescription(longDescription: String) {
        _uiState.update { it.copy(createProjectLongDescription = longDescription) }
    }

    fun updateCreateProjectLookingFor(lookingFor: String) {
        _uiState.update { it.copy(createProjectLookingFor = lookingFor) }
    }

    fun updateCreateProjectRequiredSkills(requiredSkills: String) {
        _uiState.update { it.copy(createProjectRequiredSkills = requiredSkills) }
    }

    fun updateCreateProjectTechnologies(technologies: String) {
        _uiState.update { it.copy(createProjectTechnologies = technologies) }
    }
}