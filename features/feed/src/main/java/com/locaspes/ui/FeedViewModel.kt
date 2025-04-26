package com.locaspes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.locaspes.FeedUseCase
import com.locaspes.data.UserDataRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.user.FirebaseUserActionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedUseCase: FeedUseCase, private val userDataRepository: UserDataRepository): ViewModel(){

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private var isLoading = false

    init {
        loadProjects()
    }

    fun updateCanApply(canApply: Boolean?){
        _uiState.update { it.copy(canApply = canApply) }
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

    fun changeCanApplyState(projectId: String){
        viewModelScope.launch {
            try{
                _uiState.update { it.copy( canApply = !(feedUseCase.checkIfUserAppliedToProject(userDataRepository.getUserId().first()!!, projectId))) }
                Log.d("FirebaseUserActionsRepository", uiState.value.canApply.toString())
            }
            catch (e: Exception){
                throw e
            }
        }
    }

    fun changeAuthorState(projectCard: ProjectCard){
        viewModelScope.launch {
            if (projectCard.author == feedUseCase.getCurrentUserId()){
                _uiState.update { it.copy( isAuthorState = true) }
            }
            else{
                _uiState.update { it.copy( isAuthorState = false) }
            }
        }
    }

    fun applyUserToProject(projectId: String){
        viewModelScope.launch {
            _uiState.update{it.copy(canApply = null)}
            feedUseCase.applyUserToProject(userDataRepository.getUserId().first()!!, projectId)
            changeCanApplyState(projectId)
        }
    }

    fun cancelUserApplication(projectId: String){
        viewModelScope.launch {
            _uiState.update{it.copy(canApply = null)}
            feedUseCase.cancelUserApplication(projectId)
            changeCanApplyState(projectId)

        }
    }

    fun getProjectRelatedUsers(projectId: String) {
        viewModelScope.launch {
            val projectRelatedUsers = feedUseCase.getProjectRelatedUsers(projectId)
            if (projectRelatedUsers.isSuccess) {
                Log.d("getProjectRelatedUsers", "success")
                _uiState.update { it.copy(projectParticipants = projectRelatedUsers.getOrNull()!!) }
            }
            else{
                Log.d("getProjectRelatedUsers", "error")
            }
        }
    }
}