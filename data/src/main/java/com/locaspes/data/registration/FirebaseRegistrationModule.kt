package com.locaspes.data.registration

import com.locaspes.data.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseRegistrationModule {
    @Provides
    fun provideFirebaseRegistrationRepository(
        userDataRepository: UserDataRepository
    ): FirebaseRegistrationRepository {
        return FirebaseRegistrationRepository(userDataRepository)
    }
}