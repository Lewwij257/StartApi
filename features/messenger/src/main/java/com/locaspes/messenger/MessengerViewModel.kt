package com.locaspes.messenger

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.Message
import com.locaspes.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessengerViewModel @Inject constructor(
    private val messengerUseCase: MessengerUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(MessengerUiState())
    val uiState: StateFlow<MessengerUiState> = _uiState.asStateFlow()


    init {
        loadChatList()
        loadUserProfile()
    }


    fun openChat(chatId: String){
        _uiState.update { it.copy(openChatScreen = true) }
        loadMessages(chatId)
    }

    fun loadChatList(){
        viewModelScope.launch {
            val chatsResult = messengerUseCase.getChats()
            if (chatsResult.isSuccess){
                _uiState.update { it.copy(chatList = chatsResult.getOrNull()!!) }
            }
            else{
                //TODO: показать ошибку и вынести логику в юзкейс
            }
        }
    }

    fun sendMessage(messageText: String){
        viewModelScope.launch {
            val messageToSend = Message(
                message = messageText,
                projectId = _uiState.value.openedProjectMessengerId,
                senderProfileName = _uiState.value.userProfile.username,
                senderProfileAvatar = _uiState.value.userProfile.avatarURL
            )
            messengerUseCase.sendMessage(messageToSend)
            Log.d("MessengerViewModel", "Отправлено сообщение: $messageText")
        }
    }

    fun loadMessages(projectId: String){
        viewModelScope.launch {
            messengerUseCase.getChatMessages(projectId).collect {result ->
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

    fun clearMessageTextUiState(){
        _uiState.update { it.copy(messageText = "") }
    }

    fun updateOpenedProjectMessengerId(id: String){
        _uiState.update { it.copy(openedProjectMessengerId = id) }
    }

    fun changeMessageTextState(text: String){
        _uiState.update { it.copy(messageText = text) }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val getUserProfileResult = messengerUseCase.getUserProfile()
            if (getUserProfileResult.isSuccess){
                _uiState.update { it.copy(userProfile = getUserProfileResult.getOrNull()!!) }
            }

        }
    }

}