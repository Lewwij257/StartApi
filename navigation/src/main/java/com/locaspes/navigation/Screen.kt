package com.locaspes.navigation

sealed class Screen(val route: String) {
    data object Welcome: Screen("welcome")
    data object SignUp: Screen("signup")
    data object SignIn: Screen("signin")
}