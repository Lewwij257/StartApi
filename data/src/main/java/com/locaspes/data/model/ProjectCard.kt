package com.locaspes.data.model

data class ProjectCard(
    val id: String,
    val longDescription: String,
    val lookingFor: Array<String>,
    val name: String,
    val requiredSkills: Array<String>,
    val shortDescription: String,
    val technologies: Array<String>,
    val author: String
)