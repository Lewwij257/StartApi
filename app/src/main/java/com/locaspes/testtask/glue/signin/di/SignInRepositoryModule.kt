package com.locaspes.testtask.glue.signin.di

import com.locaspes.domain.repositories.SignInRepository
import com.locaspes.testtask.glue.signin.repositories.AdapterSignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoryModule {
    @Binds
    fun bindSignInRepository(signInDataRepository: AdapterSignInRepository): SignInRepository
}