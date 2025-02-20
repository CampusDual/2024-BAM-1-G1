package com.vango.di

import com.vango.domain.respository.AuthRepository
import com.vango.domain.usecase.AuthUseCase
import com.vango.domain.usecase.AuthUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthUseCase(repo: AuthRepository): AuthUseCase {
        return AuthUseCaseImpl(repo)
    }

}