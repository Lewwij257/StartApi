package com.locaspes.messenger

import android.os.Messenger
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locaspes.data.model.ChatItem
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.widgets.ChatItemWidget
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.locaspes.data.model.Message
import com.locaspes.widgets.MessageWidget

@Composable
fun MessengerScreen(
    viewModel: MessengerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ){
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            //если открываем экран чата
            if (uiState.openChatScreen){
                Log.d("MessengerScreen", "openChatScreen = true, openChat")
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(uiState.messages) { message ->
                        MessageWidget(
                            message = message,
                            profileImagePainter =  rememberAsyncImagePainter(uiState.profileAvatarURL),
                            isUserMessage = (message.senderProfileId==viewModel.getUserId())
                            )
                    }
                }
                // Поле ввода и кнопка отправки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = uiState.messageText,
                        onValueChange = viewModel::changeMessageTextState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        placeholder = { Text("Введите сообщение...") }
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
            else{
                Log.d("MessengerScreen", "openChatScreen = false, openAllChats")
                // Экран списка чатов
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.chatList) { chatItem ->
                        ChatItemWidget(
                            onClick = {
                                viewModel.openChat(chatItem.id)
                                viewModel.updateOpenedProjectMessengerId(chatItem.id)
                            },
                            chatItem = chatItem
                        )
                    }
                }
            }
        }
    }

//    Scaffold(
//        modifier = Modifier.fillMaxSize()
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//        ) {
//            if (uiState.openMessengerScreen) {
//                Log.d("karakaka", "Чат опенмесседжскриин")
//                // Экран сообщений
//                LazyColumn(
//                    modifier = Modifier
//                        .weight(1f) // Занимает всё доступное пространство, оставляя место для TextField
//                        .padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//
//                    item {
//                        MessageWidget(Message("123", "123", "message", "123", Timestamp.now(), "Sergey"))
//                        Log.d("karakaka", "mw")
//                    }
//
//                    items(uiState.messages) { message ->
//                        MessageWidget(message)
//                    }
//                }
//                // Поле ввода и кнопка отправки
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    OutlinedTextField(
//                        value = uiState.messageText,
//                        onValueChange = viewModel::changeMessageTextState,
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = 8.dp),
//                        placeholder = { Text("Введите сообщение...") }
//                    )
//                    IconButton(
//                        onClick = {
//                            if (uiState.messageText.isNotBlank()) {
//                                viewModel.sendMessage(uiState.messageText)
//                                viewModel.clearMessageTextUiState()
//                                Log.d("karakaka", "Сообщение отправлено")
//                            }
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = android.R.drawable.ic_menu_send),
//                            contentDescription = "Отправить сообщение"
//                        )
//                    }
//                }
//            } else {
//                Log.d("karakaka", "Чаты открыты")
//                // Экран списка чатов
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(20.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(uiState.chatList) { chatItem ->
//                        ChatItemWidget(
//                            onClick = {
//                                viewModel.openChat(chatItem.id)
//                                viewModel.updateOpenedProjectMessengerId(chatItem.id)
//                            },
//                            chatItem = chatItem
//                        )
//                    }
//                }
//            }
//        }
}
