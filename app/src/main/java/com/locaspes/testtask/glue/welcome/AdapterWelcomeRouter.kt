package com.locaspes.testtask.glue.welcome

import androidx.navigation.Navigation
import com.locaspes.testtask.MAIN
import com.locaspes.testtask.R
import com.locaspes.presentation.WelcomeRouter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class AdapterWelcomeRouter @Inject constructor() : WelcomeRouter {

    override fun launchSignUp() {

        val navController = Navigation.findNavController(MAIN, R.id.nav_host_fragment )
        navController.navigate(R.id.action_welcomeFragment_to_signUpFragment)
    }

}