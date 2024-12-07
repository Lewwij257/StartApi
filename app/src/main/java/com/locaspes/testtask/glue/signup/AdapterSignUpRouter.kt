package com.locaspes.testtask.glue.signup

import androidx.navigation.Navigation
import com.locaspes.presentation.SignUpRouter
import com.locaspes.testtask.MAIN
import com.locaspes.testtask.R
import javax.inject.Inject

class AdapterSignUpRouter @Inject constructor() : SignUpRouter {

    override fun launchSignIn() {

        val navController = Navigation.findNavController(MAIN, R.id.nav_host_fragment )
        navController.navigate(R.id.action_signUpFragment_to_signInFragment)

    }

    override fun goBack() {

        val navController = Navigation.findNavController(MAIN, R.id.nav_host_fragment )
        navController.navigate(R.id.action_signUpFragment_to_welcomeFragment)

    }

}