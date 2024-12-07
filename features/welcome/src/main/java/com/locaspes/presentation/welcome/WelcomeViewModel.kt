package com.locaspes.presentation.welcome

import androidx.lifecycle.ViewModel
import com.locaspes.presentation.WelcomeRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val router: WelcomeRouter): ViewModel() {

    fun launchSignUp() {
        router.launchSignUp()
    }
}