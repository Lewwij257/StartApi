package com.locaspes.data.cloudinary

import android.content.Context
import com.locaspes.core.ImageStorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    fun provideImageStorageRepository(@ApplicationContext context: Context): ImageStorageRepository{
        return CloudinaryImageStorage(context)
    }
}