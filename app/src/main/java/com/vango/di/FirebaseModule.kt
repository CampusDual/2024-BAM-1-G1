package com.vango.di

import android.app.Activity
import android.content.Context
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.vango.data.dataSource.remote.auth.AuthRemoteGoogleClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthentication() : FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFireStore() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideSignInGoogleClient(
        credentialManager: CredentialManager,
        firebaseAuth: FirebaseAuth
    ): AuthRemoteGoogleClient {
        return AuthRemoteGoogleClient(credentialManager, firebaseAuth)
    }
}


