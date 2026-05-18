package com.example.chat

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.locaspes.model.Message
import com.locaspes.model.UserProfile
import com.google.firebase.Timestamp

class FakeChatViewModel() : IChatViewModel {

    override val uiState = MutableStateFlow(
        ChatUiState(
            messages = listOf(
                Message(
                    id = "1",
                    senderProfileId = "user1",
                    message = "Привет! Как дела?",
                    projectId = "project123",
                    date = Timestamp.now(),
                    senderProfileName = "Иван Иванов",
                    senderProfileAvatar = "https://example.com/avatar1.jpg"
                ),
                Message(
                    id = "2",
                    senderProfileId = "user2",
                    message = "Привет! Все отлично, спасибо!",
                    projectId = "project123",
                    date = Timestamp.now(),
                    senderProfileName = "Петр Петров",
                    senderProfileAvatar = "https://example.com/avatar2.jpg"
                )
            ),
            messageText = "Тестовое сообщение",
            userProfile = UserProfile(
                id = "testUser",
                username = "Тестовый Пользователь",
                email = "test@example.com",
                avatarURL = "https://example.com/test_avatar.jpg",
                profileDescription = "Тестовый профиль для демонстрации",
                skills = listOf("Kotlin", "Android", "UI/UX"),
                premium = true,
                profession = "Android Developer"
            ),
            projectId = "project123",
            isLoading = false,
            error = null
        )
    )

    override fun sendMessage(messageText: String) {
        val newMessage = Message(
            id = (uiState.value.messages.size + 1).toString(),
            senderProfileId = uiState.value.userProfile.id,
            message = messageText,
            projectId = uiState.value.projectId,
            date = Timestamp.now(),
            senderProfileName = uiState.value.userProfile.username,
            senderProfileAvatar = uiState.value.userProfile.avatarURL
        )

        uiState.value = uiState.value.copy(
            messages = uiState.value.messages + newMessage,
            messageText = ""
        )
    }

    override fun loadMessages(projectId: String) {
        uiState.value = uiState.value.copy(
            isLoading = true
        )
        // Имитация загрузки
        uiState.value = uiState.value.copy(
            isLoading = false,
            projectId = projectId
        )
    }

    override fun loadUserProfile() {
        // Уже загружено в начальном состоянии
    }

    override fun clearMessageTextUiState() {
        uiState.value = uiState.value.copy(
            messageText = ""
        )
    }

    override fun changeMessageTextState(text: String) {
        uiState.value = uiState.value.copy(
            messageText = text
        )
    }

    override fun updateProjectId(projectId: String) {

    }
}