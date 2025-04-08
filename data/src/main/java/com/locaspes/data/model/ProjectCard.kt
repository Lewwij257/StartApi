package com.locaspes.data.model

import java.util.Date

data class ProjectCard(
    val id: String = "",
    val longDescription: String = "",
    val lookingFor: List<String> = emptyList(),
    val name: String = "",
    val requiredSkills: List<String> = emptyList(),
    val shortDescription: String = "",
    val technologies: List<String> = emptyList(),
    val author: String = "",
    val createDate: Date = Date()
)