package com.locaspes.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.widgets.MainProjectCard
import com.locaspes.widgets.StandardTextField
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    var selectedProject by remember { mutableStateOf<ProjectCard?>(null) }
    val scope = rememberCoroutineScope()
    var showCreateSheet by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    fun selectProject(projectCard: ProjectCard) {
        selectedProject = projectCard
        scope.launch { sheetState.show() }
    }

    LaunchedEffect(Unit) {
        viewModel.loadUserRelatedProjects()
    }

    LaunchedEffect(uiState.successCreatingProject){
        when (uiState.successCreatingProject) {
            true -> {
                snackBarHostState.showSnackbar("Успешно!")
                viewModel.loadUserRelatedProjects()
                showCreateSheet = false
            }
            false -> snackBarHostState.showSnackbar("Ошибка!")
            //TODO:
            null -> {}
        }
    }

    if (uiState.successCreatingProject != null) {
        LaunchedEffect(Unit) {
            showCreateSheet = false

        }
    }

    Scaffold(
        snackbarHost = {SnackbarHost(snackBarHostState)},
        modifier = Modifier.fillMaxSize()
    ){
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                item {
                    OutlinedButton(
                        onClick = {
                            showCreateSheet = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding()
                            .height(50.dp)
                    ) {
                        Text(
                            text = "Новый проект",
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                if (!(uiState.userRelatedProjects.any { it.isNotEmpty() })) {
                    item {
                        MiniProjectsCategoryTitle("Тут пока ничего нет...")
                    }
                    item {
                        Text("Создайте проект или подпишитесь на проект из ленты чтобы здесь отобразились связанные с вами проекты")
                    }
                }

                if (uiState.userRelatedProjects.getOrNull(0)?.isNotEmpty() == true) {
                    item {
                        MiniProjectsCategoryTitle("Созданные проекты:")
                    }
                    items(uiState.userRelatedProjects[0]) { project ->
                        MainProjectCard(
                            modifier = Modifier.clickable {
                                selectProject(project)
                            },
                            projectCard = project
                        )
                    }
                }
                if (uiState.userRelatedProjects.getOrNull(1)?.isNotEmpty() == true) {
                    item {
                        MiniProjectsCategoryTitle("Принятые проекты:")
                    }
                    items(uiState.userRelatedProjects[1]) { project ->
                        MainProjectCard(
                            modifier = Modifier.clickable {
                                selectProject(project)
                            },
                            projectCard = project
                        )
                    }
                }
                if (uiState.userRelatedProjects.getOrNull(2)?.isNotEmpty() == true) {
                    item {
                        MiniProjectsCategoryTitle("Поданные заявки:")
                    }
                    items(uiState.userRelatedProjects[2]) { project ->
                        MainProjectCard(
                            modifier = Modifier.clickable {
                                selectProject(project)
                            },
                            projectCard = project
                        )
                    }
                }
            }

            if (showCreateSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showCreateSheet = false },
                    sheetState = rememberModalBottomSheetState(),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "Создать новый проект",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        OpenProjectCreateDialog(viewModel = viewModel, uiState = uiState)
                    }
                }
            }

            if (selectedProject != null) {
                LaunchedEffect(Unit) {
                    scope.launch { viewModel.changeCanApplyState(selectedProject!!.id) }
                }
                ModalBottomSheet(
                    onDismissRequest = {selectedProject=null},
                    sheetState = sheetState,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text(text = selectedProject!!.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp),
                            modifier = Modifier.padding(10.dp))

                        ProjectCardDescriptionText(title = "Описание:", description = selectedProject!!.longDescription)
                        ProjectCardDescriptionListText(title = "Используемые технологии:", description = selectedProject!!.technologies)
                        ProjectCardDescriptionListText(title = "Требуемые навыки:", description = selectedProject!!.requiredSkills)

                        when (uiState.canApply){
                            true ->
                                Button(
                                    onClick = {
                                        viewModel.applyUserToProject(selectedProject!!.id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                ) {
                                    Text("Подать заявку")
                                }
                            false ->
                                Button(
                                    onClick = {
                                        viewModel.cancelUserApplication(selectedProject!!.id)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ){
                                    Text("Отменить заявку")
                                }
                            null -> Button(
                                onClick = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                enabled = false
                            ) {
                                Text("Загрука...")
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ProjectCardDescriptionText(title: String, description: String){
    Text(text = title,
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.headlineMedium)
    Text(text = description,
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun ProjectCardDescriptionListText(title: String, description: List<String>){
    Text(text = title,
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.headlineMedium)
    Text(text = description.joinToString(", "),
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun MiniProjectsCategoryTitle(title: String){
    Text(text = title,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 8.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center)
}

@Composable
fun OpenProjectCreateDialog(viewModel: ProjectsViewModel, uiState: ProjectsUiState){

    Text(text = "Название проекта:",style = MaterialTheme.typography.titleMedium)
    StandardTextField(
        value = uiState.createProjectTitle,
        onValueChange = viewModel::updateCreateProjectTitle,
        placeholderText = "StartApi",
        keyboardType = KeyboardType.Text
    )

    Text(text = "Короткое описание:", style = MaterialTheme.typography.titleMedium)

    StandardTextField(
        value = uiState.createProjectShortDescription,
        onValueChange = viewModel::updateCreateProjectShortDescription,
        placeholderText = "StartApi - app for new startapp's",
        keyboardType = KeyboardType.Text
    )

    Text(text = "Длинное описание:", style = MaterialTheme.typography.titleMedium)

    StandardTextField(
        value = uiState.createProjectLongDescription,
        onValueChange = viewModel::updateCreateProjectLongDescription,
        placeholderText = "We developing this app to allow students to...",
        keyboardType = KeyboardType.Text
    )

    Text(text = "Используемые технологии:", style = MaterialTheme.typography.titleMedium)
    StandardTextField(
        value = uiState.createProjectTechnologies,
        onValueChange = viewModel::updateCreateProjectTechnologies,
        placeholderText = "Kotlin, Android, Firebase, JetpackCompose...",
        keyboardType = KeyboardType.Text
    )

    Text(text = "Требуемые навыки:", style = MaterialTheme.typography.titleMedium)
    StandardTextField(
        value = uiState.createProjectRequiredSkills,
        onValueChange = viewModel::updateCreateProjectRequiredSkills,
        placeholderText = "MVVM, Kotlin",
        keyboardType = KeyboardType.Text
    )

    Text(text = "Ищем специалистов:", style = MaterialTheme.typography.titleMedium)
    StandardTextField(
        value = uiState.createProjectLookingFor,
        onValueChange = viewModel::updateCreateProjectLookingFor,
        placeholderText = "Программист, дизайнер",
        keyboardType = KeyboardType.Text
    )

    Button(onClick = {viewModel.createProject()}) {
        Text(
            text = "Создать",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun ProjectScreenPreview(){
    //ProjectsScreen(ProjectsViewModel(projectsUseCase = ProjectsUseCase()))
}