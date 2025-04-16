package com.locaspes.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase,
    private val userDataRepository: UserDataRepository): ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun logOut() {
        viewModelScope.launch {
            settingsUseCase.logOut()
        }
    }

    //TODO:
    fun clearCache(){

    }

    //TODO:
    fun switchAppTheme(){

    }

    //TODO:
    fun saveProfile(){

    }

    suspend fun loadUserProfile(){
        val userProfile = settingsUseCase.loadUserProfile(userDataRepository.getUserId().first()!!)
    }

    fun updateProfileName(profileName: String){
        _uiState.update { it.copy(profileName = profileName) }
    }

    fun updateProfileDescription(profileDescription: String){
        _uiState.update { it.copy(profileDescription = profileDescription) }
    }

    fun updateProfileSkills(profileSkills: List<String>){
        _uiState.update { it.copy(profileSkills = profileSkills) }
    }

}
