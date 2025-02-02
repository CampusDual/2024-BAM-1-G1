package com.vango.di

import android.app.Application
import com.vango.data.dataSource.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(aplication: Application): RoomDb {
        return RoomDb.invoke(aplication)
    }

}