package com.locaspes.messenger

import android.os.Messenger
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun MessengerScreen(
    viewModel: MessengerViewModel
) {


    val uiState by viewModel.uiState.collectAsState()

    if (uiState.openMessengerScreen){

    }
    else{
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.chatList)
                    { chatItem ->
                        ChatItemWidget(onClick = {

                        }, chatItem = chatItem)
                    }
                }
            }
        }
    }


}
//
//@Composable
//@Preview
//fun MessengerScreenPreview() {
//    StellarisAppTheme {
//        MessengerScreen()
//    }
//}