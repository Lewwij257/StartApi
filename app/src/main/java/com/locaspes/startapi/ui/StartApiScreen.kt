package com.locaspes.startapi.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.locaspes.SignIn
import com.locaspes.data.UserDataRepository
import com.locaspes.data.registration.FirebaseRegistrationRepository
import com.locaspes.navigation.Screen
import com.locaspes.startapi.AppViewModel
import com.locaspes.startapi.SignUp
import com.locaspes.startapi.SignUpUseCase
import com.locaspes.startapi.SignUpViewModel
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.welcome.Welcome
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun App(modifier: Modifier){
    val viewModel: AppViewModel = hiltViewModel()
    val navController: NavHostController = rememberNavController()
    val userIdState = viewModel.isUserLoggedIn.collectAsState().value
    StellarisAppTheme {
        when (userIdState){
            null -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }
            else -> {
                val startDestination = if (userIdState) Screen.Home.route else Screen.SignUp.route
                NavHost(
                    navController = navController,
                    startDestination = startDestination
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
                        val signUpViewModel: SignUpViewModel = hiltViewModel()
                        SignUp(
                            modifier = Modifier,
                            viewModel = signUpViewModel,
                            onLogInButtonClicked = {
                                navController.navigate(Screen.SignIn.route){
                                    popUpTo(Screen.Welcome.route){
                                        inclusive = false
                                    }
                                }
                            },
                            onRegisterSuccess = {
                                navController.navigate(Screen.Home.route){
                                    popUpTo(Screen.Welcome.route){
                                        inclusive = false
                                    }
                                }
                            }
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
                    composable(Screen.Home.route) {
                        HomeNavigation(
                        )
                    }
                }
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

