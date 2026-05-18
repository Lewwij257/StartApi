package com.locaspes.projects

import com.locaspes.model.ProjectCard
import com.locaspes.model.UserProfile
import com.locaspes.model.ProjectIcon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeProjectsViewModel : IProjectsViewModel {
    private val _uiState = MutableStateFlow(
        ProjectsUiState(
            userRelatedProjects = listOf(
                listOf(
                    ProjectCard(
                        id = "1",
                        name = "Локаспес - платформа для локаций",
                        shortDescription = "Разработка платформы для поиска локаций для съемок",
                        longDescription = "Полное описание проекта по разработке платформы...",
                        lookingFor = listOf("Дизайнер", "Фронтенд"),
                        requiredSkills = listOf("Kotlin", "Compose"),
                        technologies = listOf("Android", "Firebase"),
                        author = "user1",
                        projectIcon = ProjectIcon.MobileDev
                    ),
                    ProjectCard(
                        id = "2",
                        name = "Мобильное приложение для кинотеатров",
                        shortDescription = "Приложение для бронирования билетов в кино",
                        longDescription = "Разработка современного приложения...",
                        lookingFor = listOf("Бэкенд", "Тестировщик"),
                        requiredSkills = listOf("Java", "SQL"),
                        technologies = listOf("Spring", "PostgreSQL"),
                        author = "user2",
                        projectIcon = ProjectIcon.MobileDev
                    )
                ),
                listOf(
                    ProjectCard(
                        id = "3",
                        name = "Система управления контентом",
                        shortDescription = "CMS для медиа-компаний",
                        longDescription = "Разработка системы управления...",
                        lookingFor = listOf("Fullstack"),
                        requiredSkills = listOf("JavaScript", "Node.js"),
                        technologies = listOf("React", "MongoDB"),
                        author = "user3",
                        projectIcon = ProjectIcon.Programming
                    )
                )
            ),
            projectRelatedUsers = listOf(
                listOf(
                    UserProfile(
                        id = "user1",
                        username = "Иван Иванов",
                        email = "ivan@example.com",
                        avatarURL = "https://example.com/avatar1.jpg",
                        profileDescription = "Опытный разработчик Android",
                        skills = listOf("Kotlin", "Java", "Android SDK"),
                        profession = "Android Developer"
                    ),
                    UserProfile(
                        id = "user2",
                        username = "Петр Петров",
                        email = "petr@example.com",
                        avatarURL = "https://example.com/avatar2.jpg",
                        profileDescription = "Специалист по базам данных",
                        skills = listOf("SQL", "PostgreSQL", "MySQL"),
                        profession = "Database Engineer"
                    )
                )
            ),
            selectedProject = ProjectCard(
                id = "1",
                name = "Локаспес - платформа для локаций",
                shortDescription = "Разработка платформы для поиска локаций для съемок",
                longDescription = "Полное описание проекта по разработке платформы...",
                lookingFor = listOf("Дизайнер", "Фронтенд"),
                requiredSkills = listOf("Kotlin", "Compose"),
                technologies = listOf("Android", "Firebase"),
                author = "user1",
                projectIcon = ProjectIcon.MobileDev

            ),
            canApply = true,
            createProjectTitle = "",
            createProjectShortDescription = "",
            createProjectLongDescription = "",
            createProjectLookingFor = "",
            createProjectRequiredSkills = "",
            createProjectTechnologies = "",
            editProjectId = "1",
            editProjectTitle = "Редактируемый проект",
            editProjectShortDescription = "Краткое описание для редактирования",
            editProjectLongDescription = "Полное описание для редактирования...",
            editProjectLookingFor = "Бэкенд, Тестировщик",
            editProjectRequiredSkills = "Java, SQL",
            editProjectTechnologies = "Spring, PostgreSQL",
            projectToEdit = ProjectCard(
                id = "1",
                name = "Редактируемый проект",
                shortDescription = "Краткое описание для редактирования",
                longDescription = "Полное описание для редактирования...",
                lookingFor = listOf("Бэкенд", "Тестировщик"),
                requiredSkills = listOf("Java", "SQL"),
                technologies = listOf("Spring", "PostgreSQL"),
                author = "currentUser",
                projectIcon = ProjectIcon.MobileDev
            ),
            editProjectApplies = listOf("user2", "user3"),
            editProjectAccepted = listOf("user1"),
            editProjectCreator = "currentUser"
        )
    )

    override val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    override fun saveEditedProject() {}
    override fun loadUserRelatedProjects() {}
    override fun acceptUsersApplication(projectId: String, userId: String) {}
    override fun declineUsersApplication(projectId: String, userId: String) {}
    override fun changeCanApplyState(projectId: String) {}
    override fun applyUserToProject(projectId: String) {}
    override fun cancelUserApplication(projectId: String) {}
    override fun createProject() {}
    override fun getProjectRelatedUsers(projectId: String) {}

    override fun updateCreateProjectTitle(title: String) {
        _uiState.value = _uiState.value.copy(createProjectTitle = title)
    }

    override fun updateCreateProjectShortDescription(shortDescription: String) {
        _uiState.value = _uiState.value.copy(createProjectShortDescription = shortDescription)
    }

    override fun updateCreateProjectLongDescription(longDescription: String) {
        _uiState.value = _uiState.value.copy(createProjectLongDescription = longDescription)
    }

    override fun updateCreateProjectLookingFor(lookingFor: String) {
        _uiState.value = _uiState.value.copy(createProjectLookingFor = lookingFor)
    }

    override fun updateCreateProjectRequiredSkills(requiredSkills: String) {
        _uiState.value = _uiState.value.copy(createProjectRequiredSkills = requiredSkills)
    }

    override fun updateCreateProjectTechnologies(technologies: String) {
        _uiState.value = _uiState.value.copy(createProjectTechnologies = technologies)
    }

    override fun updateProjectForEdit(project: ProjectCard) {
        _uiState.value = _uiState.value.copy(projectToEdit = project)
    }

    override fun updateEditProjectId(projectId: String) {
        _uiState.value = _uiState.value.copy(editProjectId = projectId)
    }

    override fun updateEditProjectTitle(title: String) {
        _uiState.value = _uiState.value.copy(editProjectTitle = title)
    }

    override fun updateEditProjectShortDescription(shortDescription: String) {
        _uiState.value = _uiState.value.copy(editProjectShortDescription = shortDescription)
    }

    override fun updateEditProjectLongDescription(longDescription: String) {
        _uiState.value = _uiState.value.copy(editProjectLongDescription = longDescription)
    }

    override fun updateEditProjectLookingFor(lookingFor: String) {
        _uiState.value = _uiState.value.copy(editProjectLookingFor = lookingFor)
    }

    override fun updateEditProjectRequiredSkills(requiredSkills: String) {
        _uiState.value = _uiState.value.copy(editProjectRequiredSkills = requiredSkills)
    }

    override fun updateEditProjectTechnologies(technologies: String) {
        _uiState.value = _uiState.value.copy(editProjectTechnologies = technologies)
    }

    override fun updateEditProjectApplies(applies: List<String>) {
        _uiState.value = _uiState.value.copy(editProjectApplies = applies)
    }

    override fun updateEditProjectAccepted(accepted: List<String>) {
        _uiState.value = _uiState.value.copy(editProjectAccepted = accepted)
    }

    override fun updateEditProjectCreator(creator: String) {
        _uiState.value = _uiState.value.copy(editProjectCreator = creator)
    }

    override fun updateSelectedProject(project: ProjectCard) {
        _uiState.value = _uiState.value.copy(selectedProject = project)
    }

    override fun updateSelectedIcon(projectIcon: ProjectIcon) {
        _uiState.value = _uiState.value.copy(createProjectIcon = projectIcon)

    }
}