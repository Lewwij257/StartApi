package com.locaspes.testtask.glue.signup.di

import com.locaspes.domain.repositories.SignUpRepository
import com.locaspes.testtask.glue.signup.repositories.AdapterSignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignUpRepositoryModule {
    @Binds
    fun bindSignUpRepository(signUpDataSource: AdapterSignUpRepository): SignUpRepository
}