package com.locaspes.data.model

data class Startup(
    val id: String,
    val title: String,
    val difficulty: String,
    val technologies: String,
    val shortDescription: String,
    val description: String,
    val creatorId: String
)