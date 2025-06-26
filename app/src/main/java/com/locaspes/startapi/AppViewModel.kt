package com.locaspes.startapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val userDataRepository: UserDataRepository): ViewModel() {
    private val _isUserLoggedIn = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn = _isUserLoggedIn

    init {
        viewModelScope.launch {
            userDataRepository.getUserProfile().collectLatest { userProfile ->
                _isUserLoggedIn.value = userProfile != null
            }
        }
    }

    fun setFirstOpenState(state: Boolean) {
        viewModelScope.launch {
            userDataRepository.setFirstOpenState(state)
        }
    }

    fun logOut(){
        viewModelScope.launch {
            userDataRepository.clearUserProfile()
            _isUserLoggedIn.value = false
        }
    }

}