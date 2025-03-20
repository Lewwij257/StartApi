package com.locaspes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.locaspes.FeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCase: FeedUseCase): ViewModel(){

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private var isLoading = false

    init {
        loadProjects()
    }


    fun updateSearch(searchText: String){
        _uiState.update { it.copy(search = searchText) }
    }

    fun loadProjects(){
        if (!feedUseCase.hasMoreData() || _uiState.value.isLoading) return
        viewModelScope.launch {
            isLoading = true
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            feedUseCase.loadPaginatedProjects()
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = "Ошибка загрузки: ${e.message}", isLoading = false) }
                    isLoading=false
                }
                .collect { newProjects ->
                    val updatedProjects = _uiState.value.projects.toMutableList().apply{
                        addAll(newProjects)
                    }
                    feedUseCase.updatePaginationState(updatedProjects)
                    _uiState.update {
                        it.copy(
                            projects = updatedProjects,
                            isLoading = false,
                            hasMoreData = feedUseCase.hasMoreData(),
                            errorMessage = null
                        )
                    }
                    isLoading = false
                }
        }
    }



}