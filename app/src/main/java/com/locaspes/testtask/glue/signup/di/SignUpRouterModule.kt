package com.locaspes.testtask.glue.signup.di

import com.locaspes.sign_up.SignUpRouter
import com.locaspes.testtask.glue.signup.AdapterSignUpRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignUpRouterModule {

    @Binds
    fun bindSignUpRouter(router: AdapterSignUpRouter): SignUpRouter

}