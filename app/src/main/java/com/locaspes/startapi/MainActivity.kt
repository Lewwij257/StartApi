package com.locaspes.startapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.locaspes.startapi.ui.App
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.welcome.Welcome
//import dagger.hilt.android.HiltAndroidApp
//import dagger.hilt.components.SingletonComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StellarisAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StellarisAppTheme {
        Welcome({})
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingDarkPreview() {
    StellarisAppTheme(darkTheme = false) {
        Welcome({})
    }
}