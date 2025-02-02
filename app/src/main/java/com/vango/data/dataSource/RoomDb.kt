package com.vango.data.dataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vango.data.dataSource.user.local.UserDao
import com.vango.data.dataSource.user.local.dbo.UserDbo

@Database(entities = [UserDbo::class], version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "vanGo.db"

        @Volatile
        private var instance: RoomDb? = null
        private val obj = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(obj) {
            instance ?: createDatabase(context).also { db -> instance = db }
        }

        private fun createDatabase(context: Context): RoomDb {
            return Room.databaseBuilder(
                context.applicationContext,
                RoomDb::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}