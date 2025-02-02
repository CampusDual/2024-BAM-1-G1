package com.vango.data.dataSource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vango.data.dataSource.user.local.UserDao
import com.vango.data.dataSource.user.local.dbo.UserDbo

@Database(entities = [UserDbo::class], version = 1)
abstract  class RoomDb: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "vanGo.db"

    }
}