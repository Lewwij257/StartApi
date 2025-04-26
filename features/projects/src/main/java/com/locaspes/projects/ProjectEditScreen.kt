package com.locaspes.projects

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.locaspes.widgets.ProfileListItem
import com.locaspes.widgets.StandardTextField


@Composable
fun ProjectEditScreen(viewModel: ProjectsViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    Log.d("ProjectEditScreenDebug", "Title: ${uiState.editProjectTitle}, Desc: ${uiState.editProjectShortDescription}")

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(

            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {

            Text(text = "Название проекта:",style = MaterialTheme.typography.titleMedium)
            StandardTextField(
                value = uiState.editProjectTitle,
                onValueChange = viewModel::updateEditProjectTitle,
                placeholderText = "StartApi",
                keyboardType = KeyboardType.Text
            )

            Text(text = "Короткое описание:", style = MaterialTheme.typography.titleMedium)

            StandardTextField(
                value = uiState.editProjectShortDescription,
                onValueChange = viewModel::updateEditProjectShortDescription,
                placeholderText = "StartApi - app for new startapp's",
                keyboardType = KeyboardType.Text
            )

            Text(text = "Длинное описание:", style = MaterialTheme.typography.titleMedium)

            StandardTextField(
                value = uiState.editProjectLongDescription,
                onValueChange = viewModel::updateEditProjectLongDescription,
                placeholderText = "We developing this app to allow students to...",
                keyboardType = KeyboardType.Text
            )

            Text(text = "Используемые технологии:", style = MaterialTheme.typography.titleMedium)
            StandardTextField(
                value = uiState.editProjectTechnologies,
                onValueChange = viewModel::updateEditProjectTechnologies,
                placeholderText = "Kotlin, Android, Firebase, JetpackCompose...",
                keyboardType = KeyboardType.Text
            )

            Text(text = "Требуемые навыки:", style = MaterialTheme.typography.titleMedium)
            StandardTextField(
                value = uiState.editProjectRequiredSkills,
                onValueChange = viewModel::updateEditProjectRequiredSkills,
                placeholderText = "MVVM, Kotlin",
                keyboardType = KeyboardType.Text
            )

            Text(text = "Ищем специалистов:", style = MaterialTheme.typography.titleMedium)
            StandardTextField(
                value = uiState.editProjectLookingFor,
                onValueChange = viewModel::updateEditProjectLookingFor,
                placeholderText = "Программист, дизайнер",
                keyboardType = KeyboardType.Text
            )

            Text(text = "Заявки:", style = MaterialTheme.typography.titleMedium)
            LazyColumn() {
                if (uiState.projectRelatedUsers.getOrNull(0)?.isNotEmpty() == true) {
                    item {
                        Text(text = "Поданные заявки:", style = MaterialTheme.typography.titleMedium)
                    }
                    items(uiState.projectRelatedUsers[0]){ users ->
                        ProfileListItem(
                            profile =  users,
                            showActionButtons =  true,
                            //TODO: добавить логику
                            onAccept = {},
                            onReject = {})
                    }
                }
                if (uiState.projectRelatedUsers.getOrNull(1)?.isNotEmpty() == true) {
                    item {
                        Text(text = "Участники:", style = MaterialTheme.typography.titleMedium)
                    }
                    items(uiState.projectRelatedUsers[0]){ users ->
                        ProfileListItem(
                            profile = users)
                    }
                }
            }

        }

    }
}