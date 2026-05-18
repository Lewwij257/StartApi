package com.locaspes.messenger

import com.locaspes.model.ChatItem
import com.locaspes.model.Message
import com.locaspes.model.UserProfile

data class MessengerUiState (
    val openChatScreen: Boolean = false,
    val openedProjectMessengerId: String = "",
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val chatList: List<ChatItem> = emptyList(),
    val messageText: String = "",

    val userProfile: UserProfile = UserProfile()

)