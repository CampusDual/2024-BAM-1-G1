package com.vango.di

import AuthRepositoryImpl
import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.domain.respositories.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }
}
