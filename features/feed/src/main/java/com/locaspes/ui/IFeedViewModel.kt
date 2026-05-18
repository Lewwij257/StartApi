package com.locaspes.ui

import com.locaspes.model.ProjectCard
import kotlinx.coroutines.flow.StateFlow

interface IFeedViewModel {
    val uiState: StateFlow<FeedUiState>
    fun loadProjects()
    fun updateSearch(searchText: String)
    fun applyUserToProject(projectId: String)
    fun cancelUserApplication(projectId: String)
    fun unfollowProject(projectId: String)
    fun changeCanApplyState(projectId: String)
    fun getProjectRelatedUsers(projectId: String)
    fun changeAuthorState(projectCard: ProjectCard)
}