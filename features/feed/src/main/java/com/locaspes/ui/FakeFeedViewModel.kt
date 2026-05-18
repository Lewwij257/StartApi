package com.locaspes.ui

import com.google.firebase.Timestamp
import com.locaspes.model.ProjectCard
import com.locaspes.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeFeedViewModel: IFeedViewModel {

    val _previewUiState = MutableStateFlow(
        FeedUiState(
            projects = listOf(
                ProjectCard(
                    id = "project1",
                    name = "Социальная сеть",
                    shortDescription = "Платформа для общения",
                    longDescription = "Создаем социальную сеть с функциями чата, публикаций и уведомлений.",
                    technologies = listOf("Kotlin", "Jetpack Compose", "Firebase"),
                    requiredSkills = listOf("Android Development", "UI/UX Design"),
                    lookingFor = listOf("Frontend Developer", "Backend Developer"),
                    author = "user123",
                    createDate = Timestamp.now(),
                    usersApplied = listOf("user456"),
                    usersAccepted = listOf("user789")
                ),
                ProjectCard(
                    id = "project2",
                    name = "Фитнес-приложение",
                    shortDescription = "Трекер тренировок",
                    longDescription = "Приложение для отслеживания тренировок, питания и прогресса.",
                    technologies = listOf("Java", "Room", "Retrofit"),
                    requiredSkills = listOf("Backend Development", "Database Management"),
                    lookingFor = listOf("Mobile Developer", "QA Engineer"),
                    author = "user456",
                    createDate = Timestamp.now(),
                    usersApplied = emptyList(),
                    usersAccepted = listOf("user123")
                )
            ),
            projectParticipants = listOf(
                listOf(
                    UserProfile(
                        id = "user789",
                        username = "Иван Иванов",
                        email = "ivan@example.com"
                    )
                ),
                listOf(UserProfile(id = "user456", username = "Анна Смирнова", email = "anna@example.com")),
                listOf(UserProfile(id = "user123", username = "Петр Петров", email = "petr@example.com"))
            ),
            search = "",
            isLoading = false,
            isSuccess = true,
            errorMessage = null,
            hasMoreData = true,
            canApply = true,
            isAuthorState = false,
            userId = "user123"
        )
    )

    override val uiState: StateFlow<FeedUiState> = _previewUiState.asStateFlow()

    override fun loadProjects() {
    }

    override fun updateSearch(searchText: String) {
    }

    override fun applyUserToProject(projectId: String) {
    }

    override fun cancelUserApplication(projectId: String) {
    }

    override fun unfollowProject(projectId: String) {
    }

    override fun changeCanApplyState(projectId: String) {
    }

    override fun getProjectRelatedUsers(projectId: String) {
    }

    override fun changeAuthorState(projectCard: ProjectCard) {
    }


}