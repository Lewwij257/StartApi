package com.locaspes.welcome.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.locaspes.welcome.WelcomeRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val router: WelcomeRouter): ViewModel() {

    fun launchSignUp() {
        router.launchSignIn()
    }
}