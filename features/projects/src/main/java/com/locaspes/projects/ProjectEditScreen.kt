package com.locaspes.projects

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locaspes.startapi.StartApiButton
import com.locaspes.startapi.StartApiTextField
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.theme.R
import com.locaspes.widgets.ProfileListItem
import com.locaspes.widgets.StandardTextField


@Composable
fun ProjectEditScreen(
    viewModel: ProjectsViewModel,
    onCloseEditProjectScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Log.d("ProjectEditScreenDebug", "Title: ${uiState.editProjectTitle}, Desc: ${uiState.editProjectShortDescription}")

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
                Text(
                    text = "Редактирование",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                StartApiTextField(
                    value = uiState.editProjectTitle,
                    onValueChange = viewModel::updateEditProjectTitle,
                    label = "Название проекта"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.editProjectShortDescription,
                    onValueChange = viewModel::updateEditProjectShortDescription,
                    label = "Короткое описание"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.editProjectLongDescription,
                    onValueChange = viewModel::updateEditProjectLongDescription,
                    label = "Длинное описание"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.editProjectTechnologies,
                    onValueChange = viewModel::updateEditProjectTechnologies,
                    label = "Используемые технологии"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.editProjectRequiredSkills,
                    onValueChange = viewModel::updateEditProjectRequiredSkills,
                    label = "Требуемые навыки"
                )
            }
            item {
                StartApiTextField(
                    value = uiState.editProjectLookingFor,
                    onValueChange = viewModel::updateEditProjectLookingFor,
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
                            viewModel.acceptUsersApplication(uiState.editProjectId, user.id)
                            viewModel.getProjectRelatedUsers(uiState.editProjectId)
                        },
                        onReject = {
                            viewModel.declineUsersApplication(uiState.editProjectId, user.id)
                            viewModel.getProjectRelatedUsers(uiState.editProjectId)
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
                    ProfileListItem(
                        profile = user
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
        }
    }
}
