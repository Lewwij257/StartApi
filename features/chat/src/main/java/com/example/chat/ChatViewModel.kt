package com.example.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase
): ViewModel(), IChatViewModel {

    private val _uiState = MutableStateFlow(ChatUiState())
    override val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    override fun sendMessage(messageText: String){
        viewModelScope.launch {
            val messageToSend = Message(
                senderProfileId = uiState.value.userProfile.id,
                message = messageText,
                projectId = _uiState.value.projectId,
                senderProfileName = _uiState.value.userProfile.username,
                senderProfileAvatar = _uiState.value.userProfile.avatarURL
            )
            chatUseCase.sendMessage(messageToSend)
            Log.d("MessengerViewModel", "Отправлено сообщение: $messageText")
        }
    }

    override fun loadMessages(projectId: String){
        viewModelScope.launch {
            chatUseCase.getChatMessages(projectId).collect {result ->
                if (result.isSuccess) {
                    _uiState.update { it.copy(
                        messages = result.getOrNull() ?: emptyList(),
                        isLoading = false,
                        error = null)}
                }
                else{
                    _uiState.update { it.copy(
                        messages = emptyList(),
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "неизвестная ошибка"
                    )
                    }
                }
            }
        }
    }

    override fun loadUserProfile() {
        viewModelScope.launch {
            val getUserProfileResult = chatUseCase.getUserProfile()
            if (getUserProfileResult.isSuccess){
                _uiState.update { it.copy(userProfile = getUserProfileResult.getOrNull()!!) }
            }

        }
    }

    override fun updateProjectId(projectId: String){
        _uiState.update { it.copy(projectId = projectId) }
    }

    override fun clearMessageTextUiState(){
        _uiState.update { it.copy(messageText = "") }
    }

    override fun changeMessageTextState(text: String) {
        _uiState.update { it.copy(messageText = text) }
    }

}