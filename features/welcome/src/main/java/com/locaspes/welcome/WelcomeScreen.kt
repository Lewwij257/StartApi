package com.locaspes.welcome


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.locaspes.stellaristheme.AppTypography
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.widgets.GradientButton

import com.locaspes.theme.*


@Composable
fun Welcome(
    onContinueButtonClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background), // Используем цвет из темы
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "StartApi",
                style = AppTypography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground // Семантический цвет
            )
            Text(
                text = "1.0 beta",
                color = MaterialTheme.colorScheme.onBackground,
                style = AppTypography.bodySmall,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 40.dp),
            onClick = onContinueButtonClicked
        ){
            Text(
                text = "За работу!")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun GreetingDarkPreview() {
    StellarisAppTheme(darkTheme = true) {
        Welcome({})
    }
}
@Preview(showBackground = true, widthDp = 320, heightDp = 600)
@Composable
fun GreetingLightPreview() {
    StellarisAppTheme(darkTheme = false) {
        Welcome({})
    }
}