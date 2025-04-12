package com.locaspes.data.user

import com.locaspes.data.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseUserActionsModule {
    @Provides
    fun provideFirebaseUserActionsRepository(
        userDataRepository: UserDataRepository
    ): FirebaseUserActionsRepository{

        return FirebaseUserActionsRepository(userDataRepository)
    }
}