package com.example.project_edit

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.remote.Datastore
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.user.FirebaseUserActionsRepository
import com.locaspes.model.ProjectCard
import com.locaspes.model.UserProfile
import com.locaspes.startapi.StartApiButton
import com.locaspes.startapi.StartApiTextField
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.theme.R
import com.locaspes.widgets.ProfileListItem
import com.locaspes.widgets.StandardTextField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.collections.get
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectEditScreen(
    viewModel: IProjectEditViewModel,
    onCloseEditProjectScreen: () -> Unit,
    projectToEdit: ProjectCard
) {

    var selectedUser by remember { mutableStateOf<UserProfile?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditProfileSheet by remember { mutableStateOf(false) }

    var expandedDropDownMenu by remember { mutableStateOf(false) }

    viewModel.setOnNavigateBack(onCloseEditProjectScreen)

    LaunchedEffect(uiState.successDeleteProject){
        when (uiState.successDeleteProject) {
            true -> {
                snackBarHostState.showSnackbar("Успешно!")
            }
            false -> snackBarHostState.showSnackbar("Ошибка!")
            null -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProjectToEdit(projectToEdit)
    }

    Log.d("ProjectEditScreenDebug", "Title: ${uiState.projectTitle}, Desc: ${uiState.projectShortDescription}")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {

            item {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Редактирование",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(
                            onClick = {
                                expandedDropDownMenu = !expandedDropDownMenu
                            },
                            modifier = Modifier
                                .align(alignment = Alignment.Top)
                                .size(25.dp)

                        ) {
                            Icon(painterResource(
                                id = R.drawable.img_three_dots),
                                contentDescription = ""
                            )
                            DropdownMenu(

                                expanded = expandedDropDownMenu,
                                onDismissRequest = {expandedDropDownMenu = !expandedDropDownMenu }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = "Удалить проект", color = MaterialTheme.colorScheme.error)
                                    },
                                    onClick = { viewModel.deleteProject(uiState.projectId) }
                                )
                            }
                        }

                    }
                }
            }
            item {
                StartApiTextField(
                    value = uiState.projectTitle,
                    onValueChange = viewModel::updateProjectTitle,
                    label = "Название проекта"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.projectShortDescription,
                    onValueChange = viewModel::updateProjectShortDescription,
                    label = "Короткое описание"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.projectLongDescription,
                    onValueChange = viewModel::updateProjectLongDescription,
                    label = "Длинное описание"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.projectTechnologies,
                    onValueChange = viewModel::updateProjectTechnologies,
                    label = "Используемые технологии"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.projectRequiredSkills,
                    onValueChange = viewModel::updateProjectRequiredSkills,
                    label = "Требуемые навыки"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.projectLookingFor,
                    onValueChange = viewModel::updateProjectLookingFor,
                    label = "Требуются специалисты"
                )
            }
            item {
                Text(
                    text = "Заявки:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            if (uiState.projectRelatedUsers.getOrNull(0)?.isNotEmpty() == true) {
                item {
                    Text(
                        text = "Поданные заявки:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(uiState.projectRelatedUsers[0]) { user ->
                    ProfileListItem(
                        profile = user,
                        showActionButtons = true,
                        onAccept = {
                            viewModel.acceptUsersApplication(uiState.projectId, user.id)
                            viewModel.getProjectRelatedUsers(uiState.projectId)
                        },
                        onReject = {
                            viewModel.declineUsersApplication(uiState.projectId, user.id)
                            viewModel.getProjectRelatedUsers(uiState.projectId)
                        },
                        onListItmClicked = {
                            selectedUser = user
                            showEditProfileSheet = true
//                            //TODO:
//                            ModalBottomSheet(
//                                onDismissRequest = {showEditProfileSheet = false},
//                                sheetState = sheetState,
//                                modifier = Modifier
//                            ) {
////                                Text(
////                                    text = "Редактировать профиль",
////                                    style = MaterialTheme.typography.titleLarge,
////                                    modifier = Modifier
////                                        .padding(bottom = 24.dp)
////                                        .fillMaxWidth(),
////                                    textAlign = TextAlign.Center
////                                )
//
//                                ProfileDetailModalSheet(
//                                    userProfile = user,
//                                    isUserAccepted = false,
//                                    onDismiss = {showEditProfileSheet = false},
//                                    onAcceptUser = {
//                                        viewModel.acceptUsersApplication(uiState.projectId, user.id)
//                                        viewModel.getProjectRelatedUsers(uiState.projectId)
//                                    },
//                                    onRejectUser = {
//                                        viewModel.declineUsersApplication(uiState.projectId, user.id)
//                                        viewModel.getProjectRelatedUsers(uiState.projectId)
//                                    }
//                                )
//                            }
                        }
                    )
                }
            }
            if (uiState.projectRelatedUsers.getOrNull(1)?.isNotEmpty() == true) {
                item {
                    Text(
                        text = "Участники:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                items(uiState.projectRelatedUsers[1]) { user ->
//                    ProfileListItem(
//                        profile = user
//                    )
                    Log.d("ProjectEditScreen", "Участник: ${user.username}")
                    ProfileListItem(
                        profile = user,
                        showActionButtons = false,
                        onListItmClicked = {
                            Log.d("ProjectEditScreen", "onListItemClicked")
                            selectedUser = user
                            showEditProfileSheet = true
//                            //TODO:
//                            ModalBottomSheet(
//                                onDismissRequest = {showEditProfileSheet = false},
//                                sheetState = sheetState,
//                                modifier = Modifier
//                            ) {
////                                Text(
////                                    text = "Редактировать профиль",
////                                    style = MaterialTheme.typography.titleLarge,
////                                    modifier = Modifier
////                                        .padding(bottom = 24.dp)
////                                        .fillMaxWidth(),
////                                    textAlign = TextAlign.Center
////                                )
//
//                                Log.d("ProjectEditScreen", "ProfileDetailModalShet")
//                                ProfileDetailModalSheet(
//                                    userProfile = user,
//                                    isUserAccepted = true,
//                                    onDismiss = {showEditProfileSheet = false},
//                                    onUnfollowUser = {
//                                        //TODO: СДЕЛАТЬ ВЫГОНЕНИЕ ПОЛЬЗОВАТЕЛЯ
//                                        viewModel.unfollowUser(uiState.projectId, user.id)
//                                    }
//                                )
//                            }
                        }
                    )
                }
            }
            item {
                StartApiButton(
                    onClick = {
                        viewModel.saveEditedProject()
                        onCloseEditProjectScreen()
                    },
                    text = "Сохранить",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            item {
                TextButton(
                    onClick = onCloseEditProjectScreen,
                ) {
                    Text(
                        text ="Отмена",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp,
                    )
                }
            }
        }

        if (showEditProfileSheet && selectedUser != null) {
            ModalBottomSheet(
                onDismissRequest = { showEditProfileSheet = false },
                sheetState = sheetState
            ) {
                ProfileDetailModalSheet(
                    userProfile = selectedUser!!,
                    isUserAccepted = uiState.projectRelatedUsers[1].contains(selectedUser),
                    onDismiss = { showEditProfileSheet = false },
                    onAcceptUser = {
                        viewModel.acceptUsersApplication(uiState.projectId, selectedUser!!.id)
                        viewModel.getProjectRelatedUsers(uiState.projectId)
                        showEditProfileSheet = false
                    },
                    onRejectUser = {
                        viewModel.declineUsersApplication(uiState.projectId, selectedUser!!.id)
                        viewModel.getProjectRelatedUsers(uiState.projectId)
                        showEditProfileSheet = false
                    },
                    onUnfollowUser = {
                        viewModel.unfollowUser(uiState.projectId, selectedUser!!.id)
                        viewModel.getProjectRelatedUsers(uiState.projectId)
                    })
            }
        }

    }
}

@Composable
fun ProfileDetailModalSheet(
    isUserAccepted: Boolean,
    userProfile: UserProfile,
    onDismiss: () -> Unit,
    onAcceptUser: () -> Unit = {},
    onRejectUser: () -> Unit = {},
    onUnfollowUser: () -> Unit = {}
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {

        //TODO: ДОБАВИТЬ ПРЕМИУМ!!!
        
        Image(
            painter = if (userProfile.avatarURL.isNotBlank()){
                rememberAsyncImagePainter(userProfile.avatarURL)
            } else painterResource(id = R.drawable.img_profile),
            //TODO: аватар из профиля должен браться
            modifier = Modifier
                .padding(bottom = 20.dp, top = 20.dp)
                .size(80.dp)
                .clip(CircleShape)
                //TODO: логика замены фото профиля
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .align(Alignment.CenterHorizontally),
            contentDescription = "аватар",

            )
        Text(
            text = userProfile.username,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )


        Text(
            text = userProfile.profession,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp),
            fontSize = 15.sp
        )

//        Text(
//            text = "Описание профиля:",
//            style = MaterialTheme.typography.titleMedium
//        )
        Text(
            text = userProfile.profileDescription,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center

        )

        Text(
            text = "Навыки:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = userProfile.skills.toString(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp)
            )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (isUserAccepted){
                TextButton(
                    onClick = {
                        onUnfollowUser()
                        onDismiss()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Исключить пользователя",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else{
                TextButton(
                    onClick = {
                        onAcceptUser()
                        onDismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Принять заявку"
                    )
                }

                TextButton(
                    onClick = {
                        onRejectUser()
                        onDismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Отклонить",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }



        }


    }
}

@Composable
fun ProjectEditDropDownMenu(expanded: Boolean = true){
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {}
    ) {
        DropdownMenuItem(
            text = {
                Text(text = "Удалить проект", color = MaterialTheme.colorScheme.error)
            },
            onClick = { /* Do something... */ }
        )

    }
}

@Preview
@Composable
fun ProjectEditScreenPreview(){
    StellarisAppTheme{
        ProjectEditScreen(FakeProjectViewModel(), {}, ProjectCard())
    }
}

@Preview
@Composable
fun DropDownMenuPreview(){
    StellarisAppTheme{

    }
}

@Preview
@Composable
fun ProfileDetailModalSheetPreview(

){
    StellarisAppTheme {
        ProfileDetailModalSheet(
            false,
            UserProfile(
                id = "321321321",
                username = "Lewwij257",
                profileDescription = "description of the profile where the user can tll soi=mth abiout yourslf",
                skills = listOf("lazy", "bump"),
                profession = "programmer"
            ),
            onDismiss = {},
            onAcceptUser = {},
            onRejectUser = {}
        )
    }
}