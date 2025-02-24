package com.locaspes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.stellaristheme.AppTypography
import com.locaspes.stellaristheme.StellarisAppTheme


@Composable
fun SignIn(
    modifier: Modifier = Modifier,
    onRegisterButtonClicked: () -> Unit,
    onLogInButtonClicked: () -> Unit) {

    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        Text(text = "Вход",
            style = AppTypography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(20.dp)
                .padding(bottom = 30.dp, top = 10.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 40.sp)

        Text(text = "Имя пользователя или email",
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp)

        TextField(
            value = password,
            onValueChange = { password = it },
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

        Text(text = "Пароль",
            style = AppTypography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 20.sp)


        TextField(
            value = password,
            onValueChange = { password = it },
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

        Button(
            onClick = {},
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 36.dp)
                .fillMaxWidth(),
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
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "Нет аккаунта?",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp)


            TextButton(
                onClick = onRegisterButtonClicked,
            ) {
                Text(
                    "Зарегистрироваться")
            }

        }


    }

}

@Preview(showBackground = true)
@Composable
fun SignInLightPreview(){
    StellarisAppTheme {
        SignIn(onLogInButtonClicked = {}, onRegisterButtonClicked = {})
    }
}

@Preview(showBackground = false)
@Composable
fun SignInDarkPreview(){
    StellarisAppTheme(darkTheme = true) {
        SignIn(onRegisterButtonClicked = {}, onLogInButtonClicked = {})
    }
}