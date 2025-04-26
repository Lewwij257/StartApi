package com.locaspes.widgets

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.locaspes.data.model.Message
import com.locaspes.theme.R

@Composable
fun MessageWidget(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Image(
            painter = rememberAsyncImagePainter(message.senderProfileId),
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
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = message.message
            )
        }
    }
}

@Composable
@Preview
fun MessageWidgetPreview(){
    MessageWidget(Message(
        id = "1324",
        senderProfileId = "3232321",
        message = "test message copy",
        projectId = "221323213",
        senderProfileName = "Ivan"
    ))
}