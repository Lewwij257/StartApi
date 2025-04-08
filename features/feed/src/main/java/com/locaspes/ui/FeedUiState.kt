package com.locaspes.ui

import com.locaspes.data.model.ProjectCard


data class FeedUiState (
    val search: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = true,
    val errorMessage: String? = "",
    val projects: List<ProjectCard> = emptyList(),
    val hasMoreData: Boolean = false,
    val canApply: Boolean? = null
    )