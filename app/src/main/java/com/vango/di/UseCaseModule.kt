package com.vango.di

import com.vango.domain.usecase.IsUserLoggedInUseCase
import com.vango.domain.repository.UserRepository
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
    fun provideIsUserLoggedInUseCase(
        userRepository: UserRepository
    ): IsUserLoggedInUseCase {
        return IsUserLoggedInUseCase(userRepository)
    }
}
