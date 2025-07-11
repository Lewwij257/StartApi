package com.locaspes

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.startapi.StartApiButton
import com.locaspes.startapi.StartApiTextField
import com.locaspes.stellaristheme.StellarisAppTheme


@Composable
fun SignIn(
    onSignUpButtonClickNavigation: () -> Unit,
    onSignInButtonClickNavigation: () -> Unit,
    viewModel: SignInViewModel)
    //viewModel: FakeSignInViewModel)
    {

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
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Вход",
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
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    StartApiTextField(
                        value = uiState.emailOrUsername,
                        onValueChange = viewModel::updateEmailOrUsername,
                        label = "имя или емайл"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    StartApiTextField(
                        value = uiState.password,
                        onValueChange = viewModel::updatePassword,
                        label = "пароль",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardType = KeyboardType.Password
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.errorMessage.isNotEmpty()){
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading){
                CircularProgressIndicator()
            }
            else{
                StartApiButton(
                    onClick = {
                        viewModel.signIn()
                        onSignInButtonClickNavigation()
                    },
                    text = "Продолжить",
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Нет аккаунта?")
            TextButton(onClick = onSignUpButtonClickNavigation) {
                Text(text = "Зарегистрироваться")
            }
        }
    }
}

//@Preview
//@Composable
//
//fun SignInPreview(){
//    StellarisAppTheme {
//        SignIn(
//            {},
//            {},
//            FakeSignInViewModel()
//        )
//    }
//}