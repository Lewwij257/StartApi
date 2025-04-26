package com.locaspes.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locaspes.data.model.ChatItem
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.theme.R

@Composable
fun ChatItemWidget(
    onClick: () -> Unit,
    chatItem: ChatItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Image(
            //TODO: сделать по юрл
            painter = painterResource(id = R.drawable.img_briefcase_selected), // Замени на свой ресурс
            contentDescription = "Аватар пользователя",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = chatItem.projectName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = chatItem.lastMessage
            )
        }

        Column(
            modifier = Modifier
        ) {
//            Text(
//                text = chatItem.lastMessageDate,
//                Modifier.padding(end = 16.dp)
//            )

            if (chatItem.hasNewMessages) {
                Text(
                    text = "!",
                    Modifier
                        .padding(end = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .size(24.dp)
                        .align(Alignment.End),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Composable
@Preview
fun ChatItemPreview(){
    StellarisAppTheme {
        ChatItemWidget(onClick = {},
            chatItem = ChatItem(
                id = "123",
                projectName = "test project",
                projectIconUrl = "",
                lastMessage = "last message...",
                lastMessageDate = "01.01.2001",
                hasNewMessages = true))
    }
}