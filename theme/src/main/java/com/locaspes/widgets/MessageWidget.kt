package com.locaspes.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
    profileImagePainter: Painter
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Image(
            painter = profileImagePainter,
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
                text = message.senderProfileName,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = message.message,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}



//TODO: СЕЙЧАС БЕРЁТСЯ И ЗАГРУЖАЕТСЯ ПРОФИЛЬ ПОЛЬЗОВАТЕЛЯ ИЗ ОБЛАКА А НАДО СДЕЛАТЬ ЛОКАЛЬНОЕ СОХРАНЕНИЕ