package com.locaspes.messenger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.Message
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
    private val messengerUseCase: MessengerUseCase,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    init {
        loadChatList()
        loadUserProfile()

    }

    private val _uiState = MutableStateFlow(MessengerUiState())
    val uiState: StateFlow<MessengerUiState> = _uiState.asStateFlow()

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
                senderProfileName = _uiState.value.profileName,
                senderProfileAvatar = _uiState.value.profileAvatarURL
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

    suspend fun getUserId(): String{
        viewModelScope.launch {
            return@launch messengerUseCase.getUserId()
        }
    }

    fun changeMessageTextState(text: String){
        _uiState.update { it.copy(messageText = text) }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val userId = userDataRepository.getUserId().first()!!
            messengerUseCase.loadUserProfile(userId).onSuccess { profile ->
                _uiState.update {
                    it.copy(
                        profileId = userId,
                        profileName = profile.username,
                        profileDescription = profile.profileDescription,
                        profileSkills = profile.skills,
                        profileAvatarURL = profile.avatarURL,
                        profession = profile.profession
                    )
                }
            }.onFailure { e ->
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }


//    fun changeOpenMessageScreenState(){
//        Log.d("MessengerScreen", _uiState.value.openChatScreen.toString() + " changing in changeOpenMessageScreenState before")
//        if (_uiState.value.openChatScreen){
//            _uiState.update { it.copy(openChatScreen = false) }
//        }
//        else{
//            _uiState.update { it.copy(openChatScreen = true) }
//        }
//        Log.d("MessengerScreen", _uiState.value.openChatScreen.toString() + " changing in changeOpenMessageScreenState after")
//    }
//
//    fun openChat(projectId: String){
//        changeOpenMessageScreenState()
//        loadMessages(projectId)
//    }

}