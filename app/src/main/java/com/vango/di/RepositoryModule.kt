package com.vango.di

import AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.vango.data.dataSource.remote.api.PlacesAPi
import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.data.repository.PlacesRepository
import com.vango.data.repository.PlacesRepositoryImpl
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
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        firebaseAuth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource, firebaseAuth)
    }

    @Provides
    @Singleton
    fun providePlacesRepository(placesApi: PlacesAPi): PlacesRepository {
        return PlacesRepositoryImpl(placesApi)
    }
}
