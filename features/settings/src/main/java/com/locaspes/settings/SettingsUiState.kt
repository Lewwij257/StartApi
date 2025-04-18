package com.locaspes.settings

data class SettingsUiState(
    val profileAvatarURL: String = "",
    val profileName: String = "",
    val profileId: String = "",
    val profileDescription: String = "",
    val profileSkills: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profession: String = ""
)