package com.locaspes.testtask.glue.welcome.di

import com.locaspes.testtask.glue.welcome.AdapterWelcomeRouter
import com.locaspes.presentation.WelcomeRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
interface WelcomeRouterModule {

    @Binds
    fun bindWelcomeRouter(router: AdapterWelcomeRouter): WelcomeRouter

}