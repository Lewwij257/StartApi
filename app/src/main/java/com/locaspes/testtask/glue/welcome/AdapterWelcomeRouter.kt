package com.locaspes.testtask.glue.welcome

import android.content.Context
import androidx.navigation.Navigation
import com.locaspes.testtask.MAIN
import com.locaspes.testtask.MainActivity
import com.locaspes.testtask.R
import com.locaspes.welcome.WelcomeRouter
import com.locaspes.welcome.presentation.WelcomeFragment
import dagger.hilt.internal.aggregatedroot.codegen._com_locaspes_testtask_App
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class AdapterWelcomeRouter @Inject constructor() : WelcomeRouter {

    override fun launchSignIn() {

        val navController = Navigation.findNavController(MAIN, R.id.nav_host_fragment )
        navController.navigate(R.id.action_welcomeFragment_to_signUpFragment)
    }

}