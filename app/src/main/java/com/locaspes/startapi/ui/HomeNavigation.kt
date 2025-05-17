package com.locaspes.startapi.ui

import com.locaspes.projects.ProjectEditScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.locaspes.messenger.MessengerScreen
import com.locaspes.messenger.MessengerViewModel
import com.locaspes.navigation.Screen
import com.locaspes.projects.ProjectsScreen
import com.locaspes.projects.ProjectsViewModel
import com.locaspes.settings.SettingsScreen
import com.locaspes.settings.SettingsViewModel
import com.locaspes.ui.FeedScreen
import com.locaspes.ui.FeedViewModel

@Composable
fun HomeNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()) {

    val items = listOf(Screen.Feed, Screen.Projects, Screen.Messenger, Screen.Settings)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = screen.route) },
                        label = { Text(text = screen.route) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Feed.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Feed.route) {
                val viewModel: FeedViewModel = hiltViewModel()
                FeedScreen(viewModel = viewModel)
            }
            composable(Screen.Projects.route) {
                val viewModel: ProjectsViewModel = hiltViewModel()
                ProjectsScreen(
                    viewModel = viewModel,
                    onOpenCreatedProjectScreen = {
                        navController.navigate(Screen.ProjectEdit.route)
                    } //TODO
                ) }

            composable(Screen.ProjectEdit.route) {
                val projectsBackStackEntry = remember(navController) {
                    navController.getBackStackEntry(Screen.Projects.route)
                }
                val viewModel: ProjectsViewModel = hiltViewModel(projectsBackStackEntry)
                ProjectEditScreen(
                    viewModel = viewModel,
                    onCloseEditProjectScreen = {navController.navigate(Screen.Projects.route)})
            }

            composable(Screen.Messenger.route) {
                val viewModel: MessengerViewModel = hiltViewModel()
                MessengerScreen(viewModel = viewModel) }

            composable(Screen.Settings.route) {
                val viewModel: SettingsViewModel = hiltViewModel()
                SettingsScreen(
                    viewModel = viewModel,
                    onLogoutClick = {
                        navController.navigate(Screen.SignUp.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    },
                ) }




            composable(Screen.SignUp.route) {
                App(modifier = Modifier)
            }
        }
    }
}