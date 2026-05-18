package com.locaspes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.FeedUseCase
import com.locaspes.data.UserDataRepository
import com.locaspes.model.ProjectCard
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
class FeedViewModel @Inject constructor
    (private val feedUseCase: FeedUseCase, private val userDataRepository: UserDataRepository): ViewModel(), IFeedViewModel{

    private val _uiState = MutableStateFlow(FeedUiState())
    override val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()




    init {
        loadProjects()
        viewModelScope.launch {
            _uiState.update { it.copy(userId = userDataRepository.getUserProfile().first()?.id ?: "") }
        }
    }

    fun updateCanApply(canApply: Boolean?){
        _uiState.update { it.copy(canApply = canApply) }
    }

    override fun unfollowProject(projectId: String) {
        viewModelScope.launch {
            try {
                feedUseCase.unfollowProject(projectId)
                // Обновляем локальные данные
                _uiState.update { state ->
                    val updatedProjects = state.projects.map { project ->
                        if (project.id == projectId) {
                            project.copy(usersAccepted = project.usersAccepted - uiState.value.userId)
                        } else {
                            project
                        }
                    }
                    state.copy(projects = updatedProjects)
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    override fun updateSearch(searchText: String){
        _uiState.update { it.copy(search = searchText) }
    }

    override fun loadProjects(){
        if (!feedUseCase.hasMoreData() || _uiState.value.isLoading) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            feedUseCase.loadPaginatedProjects()
                .catch { e ->
                    _uiState.update { it.copy(errorMessage = "Ошибка загрузки: ${e.message}", isLoading = false) }
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
                }
        }
    }

    override fun changeCanApplyState(projectId: String){
        viewModelScope.launch {
            try{
                _uiState.update { it.copy(
                    canApply =
                    !(feedUseCase.checkIfUserAppliedToProject(userDataRepository.getUserProfile().first()!!.id, projectId))) }
                Log.d("FirebaseUserActionsRepository", uiState.value.canApply.toString())
            }
            catch (e: Exception){
                throw e
            }
        }
    }

    override fun changeAuthorState(projectCard: ProjectCard){
        viewModelScope.launch {
            if (projectCard.author == userDataRepository.getUserProfile().first()!!.id){
                _uiState.update { it.copy( isAuthorState = true) }
            }
            else{
                _uiState.update { it.copy( isAuthorState = false) }
            }
        }
    }

    override fun applyUserToProject(projectId: String){
        viewModelScope.launch {
            _uiState.update{it.copy(canApply = null)}
            feedUseCase.applyUserToProject(userDataRepository.getUserProfile().first()!!.id, projectId)
            changeCanApplyState(projectId)
        }
    }

    override fun cancelUserApplication(projectId: String){
        viewModelScope.launch {
            _uiState.update{it.copy(canApply = null)}
            feedUseCase.cancelUserApplication(projectId)
            changeCanApplyState(projectId)
        }
    }

    override fun getProjectRelatedUsers(projectId: String) {
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