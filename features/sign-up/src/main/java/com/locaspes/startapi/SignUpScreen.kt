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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.stellaristheme.AppTypography
import com.locaspes.stellaristheme.StellarisAppTheme

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    //viewModel: FakeSignUpViewModel,
    onSignUpButtonClickNavigation: () -> Unit,
    onSignInButtonClickNavigation: () -> Unit) {

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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

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
                    .fillMaxWidth(),
                    //.clip(RoundedCornerShape(16.dp)),
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
                        label = "email"
                    )

                    StartApiTextField(
                        value = uiState.password,
                        onValueChange = viewModel::updatePassword,
                        label = "пароль"
                    )
                }

            }

            Spacer(modifier = Modifier.height(24.dp))
            if (uiState.isLoading){
                CircularProgressIndicator()
            }
            else{
                StartApiButton(
                    onClick = viewModel::signUp,
                    text = "Продолжить"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            if (uiState.errorMessage.isNotEmpty()){
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//
//
//
//        Text(
//            text = "Имя пользователя",
//            style = AppTypography.bodyLarge,
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(start = 20.dp),
//            color = MaterialTheme.colorScheme.onBackground,
//            fontSize = 20.sp
//        )
//
//        TextField(
//            value = uiState.username,
//            onValueChange = viewModel::updateUsername,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.surface,
//                    shape = RoundedCornerShape(20.dp)
//                ),
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
//            shape = RoundedCornerShape(20.dp),
//            placeholder = {
//                Text(
//                    text = "username",
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                )
//            },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Text
//            )
//        )
//
//        Text(
//            text = "Email",
//            style = AppTypography.bodyLarge,
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(start = 20.dp),
//            color = MaterialTheme.colorScheme.onBackground,
//            fontSize = 20.sp
//        )
//
//        TextField(
//            value = uiState.email,
//            onValueChange = viewModel::updateEmail,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.surface,
//                    shape = RoundedCornerShape(20.dp)
//                ),
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
//            shape = RoundedCornerShape(20.dp),
//            placeholder = {
//                Text(
//                    text = "example@gmail.com",
//                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
//                )
//            },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Email
//            )
//        )
//
//        Text(
//            text = "Пароль",
//            modifier = Modifier
//                .align(Alignment.Start)
//                .padding(start = 20.dp),
//            color = MaterialTheme.colorScheme.onBackground,
//            style = AppTypography.bodyLarge,
//            fontSize = 20.sp
//        )
//        TextField(
//            value = uiState.password,
//            onValueChange = viewModel::updatePassword,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            colors = TextFieldDefaults.colors(
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            ),
//            shape = RoundedCornerShape(20.dp),
//            placeholder = {
//                Text(
//                    text = "введите пароль",
//                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
//                )
//            },
//            keyboardOptions = KeyboardOptions.Default.copy(
//                keyboardType = KeyboardType.Text
//            ),
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//        if (uiState.isLoading){
//            CircularProgressIndicator()
//        }
//        else{
//            StartApiButton(
//                onClick = viewModel::signUp,
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 36.dp)
//                    .fillMaxWidth(),
//                text = "Продолжить")
//        }
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        Row(
//            modifier = Modifier
//                .padding(20.dp)
//                .align(alignment = Alignment.CenterHorizontally)
//        )
//        {
//            Text(
//                modifier = Modifier.align(alignment = Alignment.CenterVertically),
//                text = "Уже есть аккаунт? ",
//                color = MaterialTheme.colorScheme.onBackground,
//                fontSize = 16.sp
//            )
//            TextButton(
//                onClick = onSignInButtonClickNavigation,
//            ) {
//                Text(
//                    "Войти"
//                )
//            }
//        }
//    }
//}

//@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
//@Preview
//@Composable
//fun SignUpScreenPreview(){
//    StellarisAppTheme {
//        SignUp(
//            viewModel = FakeSignUpViewModel(),
//            {},
//            {}
//        )
//    }
//
//}
