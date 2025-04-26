package com.locaspes.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.locaspes.data.model.UserProfile
import com.locaspes.theme.R

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import com.locaspes.stellaristheme.StellarisAppTheme

@Composable
fun ProfileListItem(
    profile: UserProfile,
    showActionButtons: Boolean = false, // Параметр для отображения кнопок
    onAccept: () -> Unit = {}, // Обработчик принятия
    onReject: () -> Unit = {}, // Обработчик отклонения
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Фото профиля
        Image(
            painter = if (profile.avatarURL.isNotBlank()) {
                rememberAsyncImagePainter(profile.avatarURL)
            } else {
                painterResource(id = R.drawable.img_profile)
            },
            contentDescription = "Profile picture of ${profile.username}",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Никнейм и профессия
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = profile.username,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = profile.profession,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Кнопки принятия и отклонения
        if (showActionButtons) {
            Spacer(modifier = Modifier.width(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onReject,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Reject user",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }

                IconButton(
                    onClick = onAccept,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Accept user", tint = MaterialTheme.colorScheme.surfaceVariant)
                }

            }
        }
    }
}

@Preview
@Composable
fun ProfileListItemPreview() {
    StellarisAppTheme {
        ProfileListItem(
            profile = UserProfile(
                username = "kava",
                profession = "professor",
                avatarURL = ""
            ),
            showActionButtons = true,
            onAccept = {},
            onReject = {}
        )
    }
}

@Preview
@Composable
fun ProfileListItemNoButtonsPreview() {
    StellarisAppTheme {
        ProfileListItem(
            profile = UserProfile(
                username = "kava",
                profession = "professor",
                avatarURL = ""
            ),
            showActionButtons = false
        )
    }
}