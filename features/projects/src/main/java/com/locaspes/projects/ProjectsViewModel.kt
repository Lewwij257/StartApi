package com.locaspes.projects

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(private val projectsUseCase: ProjectsUseCase): ViewModel() {

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
}