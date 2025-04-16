package com.locaspes.settings

data class SettingsUiState(
    val profileName: String = "",
    val profileDescription: String = "",
    val profileSkills: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)