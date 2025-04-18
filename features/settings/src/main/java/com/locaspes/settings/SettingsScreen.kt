package com.locaspes.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.remote.Datastore
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.theme.R
import com.locaspes.widgets.StandardTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onLogoutClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditProfileSheet by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? ->
        uri?.let { viewModel.uploadProfileAvatar(it) }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            viewModel.clearErrorMessage()
        }
    }

    //TODO:
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {SnackbarHost(snackBarHostState)}
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
                nickname = uiState.profileName,
                userId = uiState.profileId,
                onEditProfileClick = {
                    showEditProfileSheet = true
                },
                uiState = uiState
            )

            if (showEditProfileSheet){
                ModalBottomSheet(
                    onDismissRequest = {showEditProfileSheet = false},
                    sheetState = sheetState,
                    modifier = Modifier
                ) {


                    Text(
                        text = "Редактировать профиль",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    EditProfileSheet(
                        viewModel = viewModel,
                        uiState = uiState,
                        onDismiss = {showEditProfileSheet = false},
                        onAvatarClick = {imagePicker.launch("image/*")})
                }
            }

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
    onEditProfileClick: () -> Unit,
    uiState: SettingsUiState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Image(
            painter = if (uiState.profileAvatarURL.isNotBlank()) {
                rememberAsyncImagePainter(uiState.profileAvatarURL)
            } else {
                painterResource(id = R.drawable.img_profile_selected)
            },
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
                fontSize = 10.sp,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditProfileSheet(
    viewModel: SettingsViewModel,
    uiState: SettingsUiState,
    onDismiss: () -> Unit,
    onAvatarClick: () -> Unit
) {
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp), // Дополнительный отступ снизу для клавиатуры
    ) {

        Image(
            painter = if (uiState.profileAvatarURL.isNotBlank()){
                rememberAsyncImagePainter(uiState.profileAvatarURL)
            } else painterResource(id = R.drawable.img_profile),
            //TODO: аватар из профиля должен браться
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(80.dp)
                .clip(CircleShape)
                //TODO: логика замены фото профиля
                .clickable { onAvatarClick() }
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .align(Alignment.CenterHorizontally),
            contentDescription = "аватар",

        )


        Text(
            text = "Имя пользователя:",
            style = MaterialTheme.typography.titleMedium
        )
        StandardTextField(
            value = uiState.profileName,
            onValueChange = viewModel::updateProfileName,
            placeholderText = "Как вас зовут?",
            keyboardType = KeyboardType.Text
        )

        Text(
            text = "Описание профиля:",
            style = MaterialTheme.typography.titleMedium
        )
        StandardTextField(
            value = uiState.profileDescription,
            onValueChange = viewModel::updateProfileDescription,
            placeholderText = "Расскажите о себе",
            keyboardType = KeyboardType.Text,
            singleLine = false,
            modifier = Modifier.height(100.dp)
        )

        Text(
            text = "Специальность:",
            style = MaterialTheme.typography.titleMedium
        )
        StandardTextField(
            value = uiState.profession,
            onValueChange = viewModel::updateProfession,
            placeholderText = "Программист/Дизайнер/...",
            keyboardType = KeyboardType.Text,
            singleLine = false,
            modifier = Modifier.height(100.dp)
        )

        Text(
            text = "Навыки:",
            style = MaterialTheme.typography.titleMedium
        )
        var skillsInput by remember { mutableStateOf(uiState.profileSkills.joinToString(", ")) }
        StandardTextField(
            value = skillsInput,
            onValueChange = { newValue ->
                skillsInput = newValue
                // Преобразуем строку в список навыков, разделённых запятыми
                val skillsList = newValue.split(",")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                viewModel.updateProfileSkills(skillsList)
            },
            placeholderText = "Unity, C#, AndroidStudio...",
            keyboardType = KeyboardType.Text
        )

        // Отображение текущих навыков в виде тегов
        if (uiState.profileSkills.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.profileSkills.forEach { skill ->
                    SkillTag(skill = skill)
                }
            }
        }

        Row(
            modifier = Modifier.
            fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    //TODO:
                    viewModel.saveProfile()
                    onDismiss()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Сохранить",
                    color = Color.White
                )
            }
            Button(
                onClick = { onDismiss() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "Отмена",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// Компонент для отображения тега навыка
@Composable
fun SkillTag(skill: String) {
    Text(
        text = skill,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}