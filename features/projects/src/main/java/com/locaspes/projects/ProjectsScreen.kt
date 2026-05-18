package com.locaspes.projects

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.animation.SplineBasedFloatDecayAnimationSpec
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FloatAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil3.Image
import com.locaspes.ProjectIconMapper
import com.locaspes.model.ProjectCard
import com.locaspes.model.ProjectIcon
import com.locaspes.startapi.StartApiTextField
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.widgets.MainProjectCard
import com.locaspes.widgets.StandardTextField
import kotlinx.coroutines.launch
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectsScreen(
    viewModel: IProjectsViewModel,
    onOpenCreatedProjectScreen: (project: ProjectCard) -> Unit) {

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
                            projectCard = project,
                            onClick = {
                                viewModel.updateEditProjectId(project.id)
                                viewModel.updateEditProjectTitle(project.name)
                                viewModel.updateEditProjectShortDescription(project.shortDescription)
                                viewModel.updateEditProjectLongDescription(project.longDescription)
                                viewModel.updateEditProjectLookingFor(project.lookingFor.toString())
                                viewModel.updateEditProjectRequiredSkills(project.requiredSkills.toString())
                                viewModel.updateEditProjectTechnologies(project.technologies.toString())

                                viewModel.getProjectRelatedUsers(project.id)
                                viewModel.updateSelectedProject(project)



                                onOpenCreatedProjectScreen(project)
                            }
                        )
                    }
                }
                if (uiState.userRelatedProjects.getOrNull(1)?.isNotEmpty() == true) {
                    item {
                        MiniProjectsCategoryTitle("Принятые проекты:")
                    }
                    items(uiState.userRelatedProjects[1]) { project ->
                        MainProjectCard(
                            projectCard = project,
                            onClick = {selectProject(project)}
                        )
                    }
                }
                if (uiState.userRelatedProjects.getOrNull(2)?.isNotEmpty() == true) {
                    item {
                        MiniProjectsCategoryTitle("Поданные заявки:")
                    }
                    items(uiState.userRelatedProjects[2]) { project ->
                        MainProjectCard(
                            projectCard = project,
                            onClick = {selectProject(project)}
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
                            text = if (uiState.createProjectTitle.isEmpty()) "Создать новый проект" else uiState.createProjectTitle,
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
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp),
                            modifier = Modifier.padding(10.dp))

                        ProjectCardDescriptionText(title = "Описание:", description = selectedProject!!.longDescription)

                        ProjectCardDescriptionListText(title = "Используемые технологии:", description = selectedProject!!.technologies)
                        ProjectCardDescriptionListText(title = "Требуемые навыки:", description = selectedProject!!.requiredSkills)
                        ProjectCardDescriptionListText(title = "Требуются специалисты:", description = selectedProject!!.lookingFor)

                        Text(text = "Участники:",
                            modifier = Modifier.padding(horizontal = 10.dp),
                            style = MaterialTheme.typography.titleMedium)
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            //TODO: сделать как в feed screen
                        }

                        ProjectCardDescriptionText(title = "Дата создания:", description = selectedProject!!.createDate.toString())

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
        modifier = Modifier.padding(horizontal = 10.dp),
        style = MaterialTheme.typography.titleMedium)
    Text(text = description,
        modifier = Modifier.padding(horizontal = 10.dp),
        style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun ProjectCardDescriptionListText(title: String, description: List<String>){
    Text(text = title,
        modifier = Modifier.padding(horizontal = 10.dp),
        style = MaterialTheme.typography.titleMedium)
    Text(text = description.joinToString(", "),
        modifier = Modifier.padding(horizontal = 10.dp),
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation", "RestrictedApi")
@Composable
fun OpenProjectCreateDialog(viewModel: IProjectsViewModel, uiState: ProjectsUiState){

    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )
    val textFieldModifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)


        Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){

            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val pagerState = rememberPagerState(pageCount = { enumValues<ProjectIcon>().size })
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    coroutineScope.launch {
                        viewModel.updateSelectedIcon(enumValues<ProjectIcon>()[page])
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                pageSpacing = 16.dp,
                contentPadding = PaddingValues(horizontal = (screenWidth - 64.dp) / 2 - 16.dp
                ),
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState, snapAnimationSpec = tween(durationMillis = 300) ),
                modifier = Modifier.fillMaxWidth()
                //contentPadding = PaddingValues(horizontal = 40.dp),

            ) { page ->
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .graphicsLayer {
                            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                            val scale = lerp(0.8f, 1f, 1f - abs(pageOffset))
                            scaleX = scale
                            scaleY = scale
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(ProjectIconMapper.getIconId(enumValues<ProjectIcon>()[page])),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
    }

    Text(text = "иконка", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)


    StartApiTextField(
        value = uiState.createProjectTitle,
        onValueChange = viewModel::updateCreateProjectTitle,
        label = "Название проекта",
        colors = textFieldColors,
        modifier = textFieldModifier
    )

    StartApiTextField(
        value = uiState.createProjectShortDescription,
        onValueChange = viewModel::updateCreateProjectShortDescription,
        label = "Короткое описание",
        colors = textFieldColors,
        modifier = textFieldModifier
    )

    StartApiTextField(
        value = uiState.createProjectLongDescription,
        onValueChange = viewModel::updateCreateProjectLongDescription,
        label = "Длинное описание",
        colors = textFieldColors,
        modifier = textFieldModifier
    )

    StartApiTextField(
        value = uiState.createProjectTechnologies,
        onValueChange = viewModel::updateCreateProjectTechnologies,
        label = "Используемые технологии",
        colors = textFieldColors,
        modifier = textFieldModifier
    )

    StartApiTextField(
        value = uiState.createProjectRequiredSkills,
        onValueChange = viewModel::updateCreateProjectRequiredSkills,
        label = "Требуемые навыки",
        colors = textFieldColors,
        modifier = textFieldModifier
    )

    StartApiTextField(
        value = uiState.createProjectLookingFor,
        onValueChange = viewModel::updateCreateProjectLookingFor,
        label = "Ищем специалистов",
        colors = textFieldColors,
        modifier = textFieldModifier
    )

    Button(onClick = { viewModel.createProject() }) {
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
    StellarisAppTheme {
        ProjectsScreen(FakeProjectsViewModel(), {})
    }
}