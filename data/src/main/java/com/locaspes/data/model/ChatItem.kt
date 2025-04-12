package com.locaspes.data.model

data class ChatItem (
    val projectName: String = "",
    val id: String = "",
    val projectIconUrl: String = "",
    val lastMessage: String = "",
    val lastMessageDate: String = "",
    val hasNewMessages: Boolean = false
    )