package com.locaspes.startapi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.locaspes.SignIn
import com.locaspes.navigation.Screen
import com.locaspes.startapi.SignUp
import com.locaspes.startapi.SignUpUseCase
import com.locaspes.startapi.SignUpViewModel
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.welcome.Welcome

@Composable
fun App(modifier: Modifier){
    val navController: NavHostController = rememberNavController()
    StellarisAppTheme {
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route
        ) {
            composable(Screen.Welcome.route) {
                
                StellarisAppTheme {
                    Welcome(
                        onContinueButtonClicked = {navController.navigate(Screen.SignUp.route)},
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
            composable(Screen.SignUp.route){
                SignUp(
                    modifier= Modifier,
                    viewModel = SignUpViewModel(signUpUseCase = SignUpUseCase(firebaseAuthRepository = com.locaspes.data.FirebaseAuthRepository())),
                    onLogInButtonClicked = {
                        navController.navigate(Screen.SignIn.route){
                            popUpTo(Screen.Welcome.route){
                                inclusive = false
                            }
                        }
                    },
                    onRegisterButtonClicked = {}
                )
            }
            composable(Screen.SignIn.route){
                SignIn(
                    modifier= Modifier,
                    onLogInButtonClicked = {},
                    onRegisterButtonClicked = {
                        navController.navigate(Screen.SignUp.route) {
                            popUpTo(Screen.Welcome.route){inclusive=false}
                        }})
            }
        }
    }



}
@Preview(showBackground = true)
@Composable
fun WelcomePreview(){
    StellarisAppTheme {
        Welcome(onContinueButtonClicked = {})
    }
}
