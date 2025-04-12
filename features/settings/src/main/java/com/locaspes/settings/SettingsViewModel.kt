package com.locaspes.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCase: SettingsUseCase): ViewModel() {

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
}
