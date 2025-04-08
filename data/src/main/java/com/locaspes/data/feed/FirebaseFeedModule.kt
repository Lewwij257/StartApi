package com.locaspes.data.feed

import androidx.datastore.core.DataStore
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseFeedModule {
    @Provides
    fun provideFirebaseFeedRepository(userDataRepository: UserDataRepository): FirebaseFeedRepository{
        return FirebaseFeedRepository(userDataRepository)
    }
}