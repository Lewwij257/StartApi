package com.locaspes.startapi

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.data.FirebaseAuthRepository
//import com.android.tools.screenshot.isValid
import com.locaspes.stellaristheme.AppTypography
import com.locaspes.stellaristheme.StellarisAppTheme




@Composable
fun SignUp(
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier,
    onRegisterButtonClicked: () -> Unit,
    onLogInButtonClicked: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            ){

        Text(text = "Регистрация",
            style = AppTypography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
                .padding(bottom = 30.dp, top = 10.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 40.sp)

        Text(text = "Имя пользователя",
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp)

        TextField(
            value = uiState.username,
            onValueChange = viewModel::updateUsername ,
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
                    //color = Color.White.copy(alpha = 0.7f)
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )
        Text(text = "Email",
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp)

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
                keyboardType = KeyboardType.Text
            )
        )
        Text(text = "Пароль",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            style = AppTypography.bodyLarge,
            fontSize = 20.sp)

        TextField(
            value = uiState.password,
            onValueChange = viewModel::updateUsername,
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
            )
        )

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Button(
            onClick = viewModel::signUp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 36.dp)
                .fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text(
                "Продолжить", fontSize = 20.sp)
        }


        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier
            .padding(20.dp)
            .align(alignment = Alignment.CenterHorizontally))
        {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterVertically),
                text = "Уже есть аккаунт? ",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp)

            TextButton(
                onClick = onLogInButtonClicked,
            ) {
                Text(
                    "Войти")
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun SignUpDarkPreview() {
    StellarisAppTheme(darkTheme = false) {
        SignUp(modifier = Modifier, onLogInButtonClicked = {}, onRegisterButtonClicked = {}, viewModel = SignUpViewModel(
            SignUpUseCase(
                firebaseAuthRepository = FirebaseAuthRepository()
            )
        )
        )
    }
}
@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun GreetingLightPreview() {
    StellarisAppTheme(darkTheme = true) {
        SignUp(onLogInButtonClicked = {}, onRegisterButtonClicked = {}, viewModel = SignUpViewModel(
            SignUpUseCase(
                firebaseAuthRepository = FirebaseAuthRepository()
            )
        )
        )
    }
}