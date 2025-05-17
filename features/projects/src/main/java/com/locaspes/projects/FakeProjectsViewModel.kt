package com.locaspes.projects

import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeProjectsViewModel {

    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    fun updateEditProjectTitle(it: String){

    }

    fun updateEditProjectShortDescription(it: String){

    }

    fun updateEditProjectLongDescription(it: String){}

    fun updateEditProjectTechnologies(it:String){}

    fun updateEditProjectRequiredSkills(it:String){}

    fun updateEditProjectLookingFor(it:String){}

    fun acceptUsersApplication(it:String, it2: String){}

    fun getProjectRelatedUsers(it: String){}

    fun declineUsersApplication(it: String, it2: String){}

    fun saveEditedProject() {}

}