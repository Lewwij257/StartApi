package com.locaspes.data.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseUserActionsModule {
    @Provides
    fun provideFirebaseUserActionsRepository(): FirebaseUserActionsRepository{
        return FirebaseUserActionsRepository()
    }
}