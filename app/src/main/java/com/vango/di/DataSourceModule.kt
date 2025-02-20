package com.vango.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.data.dataSource.remote.auth.AuthRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(auth,firestore)
    }
}