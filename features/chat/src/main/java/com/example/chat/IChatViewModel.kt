package com.example.chat

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IChatViewModel {
    val uiState: StateFlow<ChatUiState>
    fun sendMessage(messageText: String)
    fun loadMessages(projectId: String)
    fun loadUserProfile()
    fun clearMessageTextUiState()
    fun changeMessageTextState(text: String)
    fun updateProjectId(projectId: String)
}