package com.vango.di

import com.vango.data.dataSource.remote.user.UserRemoteDataSourceImpl
import com.vango.data.repository.UserRepositoryImpl
import com.vango.domain.respositories.AuthRepository
import com.vango.domain.respositories.UserRepository
import com.vango.domain.usecase.auth.AuthUseCase
import com.vango.domain.usecase.auth.AuthUseCaseImpl
import com.vango.domain.usecase.user.UserUseCase
import com.vango.domain.usecase.user.UserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideAuthUseCase(repository: AuthRepository): AuthUseCase {
        return AuthUseCaseImpl(repository)
    }

    @Provides
    fun provideUserUseCase(repository: UserRepository): UserUseCase {
        return UserUseCaseImpl(repository)
    }

    @Provides
    fun provideUserRepository(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRepository {
        return UserRepositoryImpl(userRemoteDataSourceImpl)
    }
}