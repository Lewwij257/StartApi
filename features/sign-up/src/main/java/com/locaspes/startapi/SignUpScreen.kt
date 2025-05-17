package com.locaspes.startapi

import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.data.registration.FirebaseRegistrationRepository
import com.locaspes.stellaristheme.AppTypography
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.utils.AuthValidationError

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable

fun SignUp(
    viewModel: SignUpViewModel,
    onRegisterSuccess: () -> Unit,
    onLogInButtonClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    StartApiTextField(
                        value = uiState.username,
                        onValueChange = viewModel::updateUsername,
                        label = "имя пользователя"
                    )
                    StartApiTextField(
                        value = uiState.email,
                        onValueChange = viewModel::updateEmail,
                        label = "емайл"
                    )
                    StartApiTextField(
                        value = uiState.password,
                        onValueChange = viewModel::updatePassword,
                        label = "пароль",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            StartApiButton(
                onClick = viewModel::signUp,
                text = "Продолжить"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Уже есть аккаунт?")
            TextButton(onClick = onLogInButtonClicked) {
                Text(text = "Войти")
            }
        }
    }
}


//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//@Composable
//@Preview
//fun SignUpPreview(){
//    StellarisAppTheme {
//        SignUp(
//            FakeSignUpViewModel(),
//            {},
//            {}
//        )
//    }
//}
//
//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//@Composable
//@Preview
//fun SignUpDarkPreview(){
//    StellarisAppTheme(darkTheme = true) {
//        SignUp(
//            FakeSignUpViewModel(),
//            {},
//            {}
//        )
//    }
//}
