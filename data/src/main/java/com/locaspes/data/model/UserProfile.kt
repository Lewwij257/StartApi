package com.locaspes.data.model

data class UserProfile(
    val id: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val avatarURL: String = "",
    val profileDescription: String = "",
    val skills: List<String> = emptyList(),
    val premium: Boolean = false,
    val projectsApplications: List<String> = emptyList(),
    val projectsCreated: List<String> = emptyList(),
    val projectsAccepted: List<String> = emptyList(),
    val profession: String = ""
)