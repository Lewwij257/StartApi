package com.locaspes.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.remote.Datastore
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.theme.R

@Composable
fun SettingsScreen(
    //TODO:
    viewModel: FakeSettingsViewModel,
    userNickname: String = "User123",
    userId: String = "ID: 123456",
    onEditProfileClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Информация о пользователе
            UserInfoSection(
                nickname = userNickname,
                userId = userId,
                onEditProfileClick = onEditProfileClick
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))

            SettingsSection(
                onLogoutClick = {
                    viewModel.logOut()
                    onLogoutClick()
                }
            )
        }
    }
}

@Composable
fun UserInfoSection(
    nickname: String,
    userId: String,
    onEditProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Image(
            painter = painterResource(id = R.drawable.img_profile_selected), // Замени на свой ресурс
            contentDescription = "Аватар пользователя",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        // Никнейм и ID
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = nickname,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "id: $userId",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Text(
            text = "Редактировать",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onEditProfileClick() }
                .padding(8.dp)
        )
    }
}

@Composable
fun SettingsSection(
    onLogoutClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Тёмная тема",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            //TODO:
            Switch(
                checked = false,
                onCheckedChange = { }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Уведомления",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            //TODO:
            Switch(
                checked = true,
                onCheckedChange = { }
            )
        }
        Text(
            text = "Очистить кэш",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                //TODO:
                .clickable {  }
                .padding(vertical = 12.dp)
        )

        Button(
            //TODO:
            onClick = {
                onLogoutClick()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = "Выйти из аккаунта",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview
fun SettingsScreenPreview() {
    StellarisAppTheme {
        SettingsScreen(
            viewModel = FakeSettingsViewModel(),
            userNickname = "User",
            userId = "32142114",
            onEditProfileClick = {},
            onLogoutClick = {}
        )
    }
}