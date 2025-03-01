package com.vango.di

import com.vango.domain.respositories.AuthRepository
import com.vango.domain.respositories.UserRepository
import com.vango.domain.usecase.auth.AuthUseCase
import com.vango.domain.usecase.auth.AuthUseCaseImpl
import com.vango.domain.usecase.user.UserUseCase
import com.vango.domain.usecase.user.UserUseCaseImpl
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
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase {
        return AuthUseCaseImpl(
            repository

        )
    }

}