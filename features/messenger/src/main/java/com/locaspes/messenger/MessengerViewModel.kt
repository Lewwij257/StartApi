package com.locaspes.messenger

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessengerViewModel @Inject constructor(
    private val messengerUseCase: MessengerUseCase
) : ViewModel() {

    init {
        loadChats()
    }

    private val _uiState = MutableStateFlow(MessengerUiState())
    val uiState: StateFlow<MessengerUiState> = _uiState.asStateFlow()

    fun loadMessages(projectId: String) {
        viewModelScope.launch {
            _uiState.value = MessengerUiState(isLoading = true)
            messengerUseCase.getChatMessages(projectId).collect { result ->
                if (result.isSuccess) {
                    _uiState.value = MessengerUiState(
                        messages = result.getOrNull() ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = MessengerUiState(
                        messages = emptyList(),
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Неизвестная ошибка"
                    )
                }
            }
        }
    }

    fun loadChats(){
        viewModelScope.launch {
            val chatsResult = messengerUseCase.getChats()
            if (chatsResult.isSuccess){
                _uiState.update { it.copy(chatList = chatsResult.getOrNull()!!) }
                Log.d("special", _uiState.value.chatList.toString())

            }
            else{
                //TODO: показать ошибку и вынести логику в юзкейс
            }
        }
    }
}