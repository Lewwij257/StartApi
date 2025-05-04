package com.locaspes.startapi

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.stellaristheme.AppTypography
import com.locaspes.utils.AuthValidationError

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    onRegisterSuccess: () -> Unit,
    onLogInButtonClicked: () -> Unit) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = "Регистрация",
            style = AppTypography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
                .padding(bottom = 30.dp, top = 10.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 40.sp
        )

        Text(
            text = "Имя пользователя",
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )

        TextField(
            value = uiState.username,
            onValueChange = viewModel::updateUsername,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "username",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        uiState.validationErrors.forEach { error ->
            when (error) {
                AuthValidationError.EmptyUsername -> Text(
                    text = "Имя пользователя не может быть пустым",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                AuthValidationError.ShortUsername -> Text(
                    text = "Имя пользователя должно быть длиннее 3 символов",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                AuthValidationError.LongUsername -> Text(
                    text = "Дружище, полегче, очень длинно",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                else -> Unit
            }
        }

        Text(
            text = "Email",
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp
        )

        TextField(
            value = uiState.email,
            onValueChange = viewModel::updateEmail,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "example@gmail.com",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
        )

        uiState.validationErrors.forEach { error ->
            when (error) {
                AuthValidationError.EmptyEmail -> Text(
                    text = "Email не может быть пустым",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                AuthValidationError.InvalidEmail -> Text(
                    text = "Некорректный формат email",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                else -> Unit
            }
        }

        Text(
            text = "Пароль",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = AppTypography.bodyLarge,
            fontSize = 20.sp
        )
        TextField(
            value = uiState.password,
            onValueChange = viewModel::updatePassword,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp),
            placeholder = {
                Text(
                    text = "введите пароль",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        uiState.validationErrors.forEach { error ->
            when (error) {
                AuthValidationError.EmptyPassword -> Text(
                    text = "Пароль не должен быть пустым",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                AuthValidationError.ShortPassword -> Text(
                    text = "Пароль должен быть длиннее 5 символов",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 20.dp)
                )
                else -> Unit
            }
        }

        if (uiState is SignUpUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (uiState is SignUpUiState.Error && (uiState as SignUpUiState.Error).message.isNotEmpty()) {
            Text(
                text = (uiState as SignUpUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        if (uiState is SignUpUiState.Success){
            LaunchedEffect(Unit){
                onRegisterSuccess()
            }
        }

        Button(
            onClick = viewModel::signUp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 36.dp)
                .fillMaxWidth(),
            enabled = uiState !is SignUpUiState.Loading
        ) {
            Text(
                "Продолжить", fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .padding(20.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
        {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "Уже есть аккаунт? ",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
            TextButton(
                onClick = onLogInButtonClicked,
            ) {
                Text(
                    "Войти"
                )
            }
        }
    }
}