package com.locaspes.navigation

sealed class Screen(val route: String) {
    data object Welcome: Screen("welcome")
    data object SignUp: Screen("signup")
    data object SignIn: Screen("signin")

    data object Home : Screen("home")

    data object Feed : Screen("feed")
    data object Projects : Screen("projects")
    data object Messenger : Screen("messenger")
    data object Settings : Screen("settings")
}