package com.locaspes.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.widgets.MainProjectCard

@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel){
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {

            item {
                OutlinedButton (
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding()
                        .height(50.dp)
                ) {
                    Text(text = "Добавить новый проект",
                        color = MaterialTheme.colorScheme.onSurface)
                }
            }

            itemsIndexed(uiState.userRelatedProjects) { index: Int, project ->
                MainProjectCard(modifier = Modifier
                    .clickable {  },
                    projectCard = project)
            }
        }
    }
}

@Composable
@Preview
fun ProjectScreenPreview(){
    //ProjectsScreen(ProjectsViewModel(projectsUseCase = ProjectsUseCase()))
}