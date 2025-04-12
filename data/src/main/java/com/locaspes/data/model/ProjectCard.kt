package com.locaspes.data.model

import com.google.firebase.Timestamp
import java.util.Date

data class ProjectCard(
    var id: String = "",
    val name: String = "",
    val shortDescription: String = "",
    val longDescription: String = "",
    val lookingFor: List<String> = emptyList(),
    val requiredSkills: List<String> = emptyList(),
    val technologies: List<String> = emptyList(),
    var author: String = "",
    var createDate: Timestamp = Timestamp.now()
)