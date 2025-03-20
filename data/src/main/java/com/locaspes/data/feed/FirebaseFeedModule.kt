package com.locaspes.data.feed

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseFeedModule {
    @Provides
    fun provideFirebaseFeedRepository(): FirebaseFeedRepository{
        return FirebaseFeedRepository()
    }
}