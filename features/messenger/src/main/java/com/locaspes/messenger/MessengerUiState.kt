package com.locaspes.messenger

import com.locaspes.data.model.ChatItem
import com.locaspes.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class MessengerUiState (
    val openChatScreen: Boolean = false,
    val openedProjectMessengerId: String = "",
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val chatList: List<ChatItem> = emptyList(),
    val messageText: String = "",

    val profileId: String = "",
    val profileName: String = "",
    val profileDescription: String = "",
    val profileSkills: List<String> = emptyList(),
    val profileAvatarURL: String = "",
    val profession: String = ""



)