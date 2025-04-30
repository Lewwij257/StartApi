package com.locaspes.settings

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.auth.User
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.UserProfile
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
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    fun logOut() {
        viewModelScope.launch {
            settingsUseCase.logOut()
        }
    }

    fun uploadProfileAvatar(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            settingsUseCase.uploadAvatar(uri).onSuccess { url ->
                _uiState.update {
                    it.copy(
                        profileAvatarURL = url,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }.onFailure { e ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Ошибка загрузки аватарки"
                    )
                }
            }
        }
    }

    fun updateProfileName(profileName: String) {
        _uiState.update { it.copy(profileName = profileName) }
    }

    fun updateProfileDescription(profileDescription: String) {
        _uiState.update { it.copy(profileDescription = profileDescription) }
    }

    fun updateProfileSkills(profileSkills: List<String>) {
        _uiState.update { it.copy(profileSkills = profileSkills) }
    }

    fun updateProfession(profession: String) {
        _uiState.update { it.copy(profession = profession) }
    }


    fun saveProfile() {
        Log.d("saveprofilelog", "viewmodel")
        viewModelScope.launch {
            val userProfile = UserProfile(
                id = _uiState.value.profileId,
                username = _uiState.value.profileName,
                profileDescription = _uiState.value.profileDescription,
                skills = _uiState.value.profileSkills,
                avatarURL = _uiState.value.profileAvatarURL,
                profession = _uiState.value.profession)
            settingsUseCase.saveUserProfile(userProfile)
        }

    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val userId = userDataRepository.getUserProfile().first()!!.id
            settingsUseCase.loadUserProfile(userId).onSuccess { profile ->
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
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}