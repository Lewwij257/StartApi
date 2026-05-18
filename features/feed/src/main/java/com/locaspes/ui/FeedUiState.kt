package com.locaspes.ui

import com.locaspes.model.ProjectCard
import com.locaspes.model.UserProfile


data class FeedUiState (
    val isAuthorState: Boolean = false,
    val search: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = true,
    val errorMessage: String? = "",
    val projects: List<ProjectCard> = emptyList(),
    val hasMoreData: Boolean = false,
    val canApply: Boolean? = null,
    val projectParticipants: List<List<UserProfile>> = emptyList(),

    var userId: String = ""
    )