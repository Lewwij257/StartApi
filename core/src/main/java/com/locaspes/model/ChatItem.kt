package com.locaspes.model

data class ChatItem (
    val projectName: String = "",
    val id: String = "",
    val lastMessage: String = "",
    val lastMessageDate: String = "",
    val hasNewMessages: Boolean = false,
    val icon: ProjectIcon = ProjectIcon.Default
    )