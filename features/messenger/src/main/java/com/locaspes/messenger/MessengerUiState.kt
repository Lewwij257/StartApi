package com.locaspes.messenger

import com.locaspes.data.model.ChatItem
import com.locaspes.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class MessengerUiState (
    val openMessengerScreen: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
    val chatList: List<ChatItem> = emptyList()
)