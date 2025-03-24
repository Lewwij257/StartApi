package com.locaspes.ui

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.locaspes.FeedUseCase
import com.locaspes.data.feed.FirebaseFeedRepository
import com.locaspes.data.model.ProjectCard
import com.locaspes.widgets.MainProjectCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    modifier: Modifier,
    viewModel: FeedViewModel){

    var selectedProject by remember { mutableStateOf<ProjectCard?>(null)}
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

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
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp))
                    Text(text = selectedProject!!.longDescription,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp))
                }
            }
        }
    }
}

@Composable
fun ProjectCardDetail(projectCard: ProjectCard){
    Dialog(onDismissRequest =  {}) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(0.9f),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = projectCard.name)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = projectCard.longDescription)
            }
        }
    }
}

@Composable
@Preview
fun FeedScreenPreview(){
    FeedScreen(modifier = Modifier, viewModel = FeedViewModel(feedUseCase = FeedUseCase(
        FirebaseFeedRepository()
    )))
}
