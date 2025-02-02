package com.vango.di

import android.content.Context
import androidx.room.Room
import com.vango.data.dataSource.RoomDb
import com.vango.data.dataSource.user.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomDb {
        return Room.databaseBuilder(
            context,
            RoomDb::class.java,
            "vanGo.db"
        ).build()
    }

    @Provides
    fun provideUserDao(database: RoomDb): UserDao {
        return database.userDao()
    }
}
