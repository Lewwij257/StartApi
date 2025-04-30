package com.locaspes.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.locaspes.data.model.Message
import com.locaspes.theme.R

@Composable
fun MessageWidget(
    message: Message,
    profileImagePainter: Painter,
    isUserMessage: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start
    ) {
        if (isUserMessage) {
            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = message.senderProfileName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(12.dp)
                ) {
                    Text(
                        text = message.message,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Image(
                painter = profileImagePainter,
                contentDescription = "Ваш аватар",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = profileImagePainter,
                contentDescription = "Аватар собеседника",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = message.senderProfileName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(12.dp)
                ) {
                    Text(
                        text = message.message,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewMessageWidget() {
    MaterialTheme {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
            MessageWidget(
                message = Message(
                    senderProfileName = "Друг",
                    message = "Привет! Как дела?"
                ),
                profileImagePainter = painterResource(R.drawable.img_profile),
                isUserMessage = false
            )
            MessageWidget(
                message = Message(
                    senderProfileName = "Вы",
                    message = "Отлично, спасибо!"
                ),
                profileImagePainter = painterResource(R.drawable.img_profile),
                isUserMessage = true
            )
        }
    }
}


//TODO: СЕЙЧАС БЕРЁТСЯ И ЗАГРУЖАЕТСЯ ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ ИЗ ОБЛАКА А НАДО СДЕЛАТЬ ЛОКАЛЬНОЕ СОХРАНЕНИЕ