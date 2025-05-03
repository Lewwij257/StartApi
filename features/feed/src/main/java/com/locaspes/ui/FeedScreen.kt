package com.locaspes.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.locaspes.FeedUseCase
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.data.user.FirebaseUserActionsRepository
import com.locaspes.widgets.MainProjectCard
import com.locaspes.widgets.ProfileListItem
import kotlinx.coroutines.launch
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel){

    var selectedProject by remember { mutableStateOf<ProjectCard?>(null)}
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    //10 readings!!! too much for screen switch
//    LaunchedEffect(Unit) {
//        viewModel.loadProjects()
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TextField(
                value = uiState.search,
                onValueChange = viewModel::updateSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(20.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(
                        text = "Поиск по ключевым словам",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
            )
            //TODO ДОБАВИТЬ ФИЛЬТРЫ
        }

        LazyColumn {
            itemsIndexed(uiState.projects) { index, project ->
                MainProjectCard(
                    modifier = Modifier.clickable {
                        selectedProject = project
                        scope.launch { sheetState.show() }
                    },
                    projectCard = project
                )
                if (index == uiState.projects.size - 1 && !uiState.isLoading) {
                    viewModel.loadProjects()
                }
            }
        }


        if (selectedProject!=null){
            LaunchedEffect(Unit) {
                //null - user is author
                scope.launch { viewModel.changeCanApplyState(selectedProject!!.id) }
                scope.launch { viewModel.getProjectRelatedUsers(selectedProject!!.id) }
                scope.launch { viewModel.changeAuthorState(selectedProject!!) }
            }
            ModalBottomSheet(
                onDismissRequest = {selectedProject = null},
                sheetState = sheetState,
                modifier = Modifier
                    .fillMaxHeight()
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
                    ProjectCardDescriptionListText(title = "Требуются специалисты:", description = selectedProject!!.lookingFor)

                    Text(text = "Участники:",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        style = MaterialTheme.typography.titleMedium)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        if (uiState.projectParticipants.getOrNull(0)?.isNotEmpty() == true){
                            items(uiState.projectParticipants[0]){user ->
                                ProfileListItem(
                                    profile = user
                                    )
                            }
                            Log.d("getProjectRelatedUsers", "have users")
                        }
                        else{
                            Log.d("getProjectRelatedUsers", "no users")
                        }

                        if (uiState.projectParticipants.getOrNull(1)?.isNotEmpty() == true){
                            items(uiState.projectParticipants[1]){user ->
                                ProfileListItem(user)
                            }
                            Log.d("getProjectRelatedUsers", "have users")
                        }
                        else{
                            Log.d("getProjectRelatedUsers", "no users")
                        }
                        item {
                            Text(text = "Создатель:",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                style = MaterialTheme.typography.titleMedium)
                        }

                        if (uiState.projectParticipants.getOrNull(2)?.isNotEmpty() == true){
                            items(uiState.projectParticipants[2]){user ->
                                ProfileListItem(user)
                            }
                            Log.d("getProjectRelatedUsers", "have users")

                        }

                    }

                    ProjectCardDescriptionText(title = "Дата создания:", description = selectedProject!!.createDate.toString())

                    Log.d("FeedScreen", "users accepted: ${selectedProject!!.usersAccepted}, userId: ${viewModel.userId}")
                    if (selectedProject!!.usersAccepted.contains(viewModel.userId)) {
                        Log.d("FeedScreen", "user accepted")
                        Button(
                            onClick = {
                                viewModel.unfollowProject(selectedProject!!.id)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                        ) {
                            Text("Отписаться от проекта")
                            //TODO: Не меняется кнопка!!! и не работает ёу
                        }
                    }
                    else if (!uiState.isAuthorState){
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
                                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
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

                    else{
                        Text("Это ваш проект!",
                            modifier = Modifier.fillMaxWidth()
                                .padding(20.dp),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center)
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
@Preview
fun FeedScreenPreview(){
    //val viewModel: FeedViewModel
    //FeedScreen(modifier = Modifier)
}
