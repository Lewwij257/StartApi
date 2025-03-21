package com.locaspes.data.registration

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseRegistrationModule {
    @Provides
    fun provideFirebaseRegistrationRepository(): FirebaseRegistrationRepository {
        return FirebaseRegistrationRepository()
    }
}