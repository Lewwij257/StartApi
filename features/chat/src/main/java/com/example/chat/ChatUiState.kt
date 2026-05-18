package com.example.chat

import com.locaspes.model.Message
import com.locaspes.model.UserProfile

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val message: String = "",

    val userProfile: UserProfile = UserProfile(),
    val projectId: String = "",

    val isLoading: Boolean = false,
    val error: String? =  null,

    val messageText: String = ""
)