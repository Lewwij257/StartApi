package com.example.chat

import android.os.Debug
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.locaspes.model.Message
import com.locaspes.model.UserProfile
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.widgets.MessageWidget

@Composable
fun ChatScreen(
    viewModel: IChatViewModel,
    chatId: String,
    onCloseChatScreen: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadMessages(chatId)
        viewModel.updateProjectId(chatId)
    }

    val hideKeyBoardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                items(uiState.messages){ message ->
                    MessageWidget(
                    message = message,
                    profileImagePainter = rememberAsyncImagePainter(message.senderProfileAvatar),
                    isUserMessage = (message.senderProfileId==uiState.userProfile.id))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .imePadding()
            ) {
                OutlinedTextField(
                    value = uiState.messageText,
                    onValueChange = viewModel::changeMessageTextState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Введите сообщение...") },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        hideKeyBoardController?.hide()
                    })
                )
                IconButton(
                    onClick = {
                        if (uiState.messageText.isNotBlank()) {
                            viewModel.sendMessage(uiState.messageText)
                            viewModel.clearMessageTextUiState()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_send),
                        contentDescription = "Отправить сообщение"
                    )
                }
            }

        }



//        innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize(),
//        ) {
//            items(uiState.messages){ message ->
//                Log.d("ChatScreen", "user loaded: ${message.senderProfileId}, avatar: ${message.senderProfileAvatar}")
//
//                MessageWidget(
//                    message = message,
//                    profileImagePainter = rememberAsyncImagePainter(message.senderProfileAvatar),
//                    isUserMessage = (message.senderProfileId==uiState.userProfile.id))
//            }
//        }
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ){
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .imePadding()
//                    .align(Alignment.BottomCenter)
//            ){
//                OutlinedTextField(
//                    value = uiState.messageText,
//                    onValueChange = viewModel::changeMessageTextState,
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(end = 8.dp),
//                    placeholder = { Text("Введите сообщение...") },
//                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                    keyboardActions = KeyboardActions(onDone = {
//                        hideKeyBoardController?.hide()
//                    })
//                )
//                IconButton(
//                    onClick = {
//                        if (uiState.messageText.isNotBlank()) {
//                            viewModel.sendMessage(uiState.messageText)
//                            viewModel.clearMessageTextUiState()
//                        }
//                    }
//                ) {
//                    Icon(
//                        painter = painterResource(id = android.R.drawable.ic_menu_send),
//                        contentDescription = "Отправить сообщение"
//                    )
//                }
//            }
//        }
    }

}

@Preview
@Composable
fun ChatScreenPreview(){
    StellarisAppTheme {
        val items = listOf(
            Message("s","1","hi","2", Timestamp.now(), "", ""),
            Message("s","1","My name is Jonathan","2", Timestamp.now(), "", "") ,
                    Message("s","2","This is just a huge message to test everything in compose is fine, and looks like it isn't","2", Timestamp.now(), "", "")

        )
        ChatScreen(
            viewModel = FakeChatViewModel(),
            chatId = "",
            onCloseChatScreen = {}
        )
    }
}