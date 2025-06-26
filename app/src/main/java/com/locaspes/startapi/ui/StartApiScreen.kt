package com.locaspes.startapi.ui

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.locaspes.SignIn
import com.locaspes.SignInViewModel
import com.locaspes.messenger.MessengerScreen
import com.locaspes.messenger.MessengerViewModel
import com.locaspes.projects.ProjectEditScreen
import com.locaspes.projects.ProjectsScreen
import com.locaspes.projects.ProjectsViewModel
import com.locaspes.settings.SettingsScreen
import com.locaspes.settings.SettingsViewModel
import com.locaspes.startapi.AppViewModel
import com.locaspes.startapi.SignUp
import com.locaspes.startapi.SignUpViewModel
import com.locaspes.startapi.navigation.Screen
import com.locaspes.stellaristheme.StellarisAppTheme
import com.locaspes.ui.FeedScreen
import com.locaspes.ui.FeedViewModel
import com.locaspes.welcome.Welcome

@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun App() {
    val viewModel: AppViewModel = hiltViewModel()
    val navController: NavHostController = rememberNavController()
    //val isUserLoggedIn = viewModel.isUserLoggedIn.collectAsState().value
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    if (isUserLoggedIn == null) {
        return StellarisAppTheme {
            // Можно добавить индикатор загрузки
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }

    val items: List<Screen> = listOf(Screen.Feed, Screen.Projects, Screen.Messenger, Screen.Settings)

    val startApiEnterTransition = scaleIn(
        initialScale = 0.9f,
        animationSpec = spring(dampingRatio = 0.8f, stiffness = 200f)
    ) + fadeIn(animationSpec = tween(200))

    val startApiExitTransition = scaleOut(
        targetScale = 0.9f,
        animationSpec = tween(400)
    ) + fadeOut(animationSpec = tween(200))
    StellarisAppTheme {
        val startDestination = if (isUserLoggedIn==true) Screen.Feed.route else Screen.SignUp.route
        Scaffold(
            bottomBar = {
                if(isUserLoggedIn==true){
                    NavigationBar(){
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        items.forEach{screen ->
                            NavigationBarItem(
                                icon =  {
                                    Icon(
                                        painter = painterResource(screen.barIconId),
                                        contentDescription = screen.route,
                                        modifier = Modifier.size(35.dp))},
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route){
                                        popUpTo(navController.graph.findStartDestination().id){
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }){
            paddingValues ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(paddingValues)
            ){

                composable(Screen.Welcome.route) {
                    Welcome(
                        onContinueButtonClicked = {
                            navController.navigate(Screen.SignUp.route)
                            viewModel.setFirstOpenState(false)
                        }
                    )
                }

                composable(Screen.SignUp.route) {
                    val signUpViewModel: SignUpViewModel = hiltViewModel()
                    SignUp(
                        viewModel = signUpViewModel,
                        onSignUpButtonClickNavigation = {
                            navController.navigate(Screen.Feed.route){
                                popUpTo(Screen.Home.route){ inclusive = true}
                            }
                        },
                        onSignInButtonClickNavigation = {
                            navController.navigate(Screen.SignIn.route)
                        }
                    )
                }

                composable(Screen.SignIn.route) {
                    val signInViewModel: SignInViewModel = hiltViewModel()
                    SignIn(
                        viewModel = signInViewModel,
                        onSignInButtonClickNavigation = {
                            navController.navigate(Screen.Feed.route){
                                popUpTo(Screen.Home.route){inclusive = true}
                            }
                        },
                        onSignUpButtonClickNavigation = {
                            navController.navigate(Screen.SignUp.route)
                        },
                    )
                }

                composable(Screen.Feed.route) {
                    val feedViewModel: FeedViewModel = hiltViewModel()
                    FeedScreen(viewModel = feedViewModel)
                }

                composable(Screen.Projects.route) {
                    val projectsViewModel: ProjectsViewModel = hiltViewModel()
                    ProjectsScreen(
                        viewModel = projectsViewModel,
                        onOpenCreatedProjectScreen = {
                            navController.navigate(Screen.ProjectEdit.route)
                        }
                    )
                }

                composable(Screen.ProjectEdit.route){
                    val projectsViewModel: ProjectsViewModel = hiltViewModel()
                    ProjectEditScreen(
                        viewModel = projectsViewModel,
                        onCloseEditProjectScreen = {
                            navController.navigate(Screen.Projects.route)
                        }
                    )
                }

                composable(Screen.Messenger.route) {
                    val messengerViewModel: MessengerViewModel = hiltViewModel()
                    MessengerScreen(viewModel = messengerViewModel)
                }

                composable(Screen.Settings.route) {
                    val settingsViewModel: SettingsViewModel = hiltViewModel()
                    SettingsScreen(
                        viewModel = settingsViewModel,
                        onLogOutClick = {
                            viewModel.logOut()
                            navController.navigate(Screen.SignIn.route) {
                            popUpTo(0){
                                inclusive = false } }
                        }
                    )
                }
            }
        }
    }
}