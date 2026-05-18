package com.locaspes.startapi.navigation

sealed class Screen(val route: String, val barIconId: Int) {
    //icon doesn't need
    data object Welcome: Screen("welcome", 0)
    data object SignUp: Screen("signup", 0)
    data object SignIn: Screen("signin", 0)
    data object Home : Screen("home", 0)
    data object ProjectEdit : Screen("projectEdit/{projectJson}", 0)

    data object Feed : Screen("feed", com.locaspes.theme.R.drawable.img_home_black)
    data object Projects : Screen("projects", com.locaspes.theme.R.drawable.img_briefcase_selected)
    data object Messenger : Screen("messenger", com.locaspes.theme.R.drawable.img_messenger_black)

    data object Chat: Screen("chat/{chatId}", 0)

    data object Settings : Screen("settings", com.locaspes.theme.R.drawable.img_settings_black)
}