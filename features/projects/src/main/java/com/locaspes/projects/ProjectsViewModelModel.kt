package com.locaspes.projects

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    @ViewModelScoped
    fun provideProjectsViewModel(projectsUseCase: ProjectsUseCase): ProjectsViewModel {
        return ProjectsViewModel(projectsUseCase)
    }
}