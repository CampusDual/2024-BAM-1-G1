package com.vango.data.dataSource.user.local

import androidx.room.Dao
import androidx.room.Query
import com.vango.data.dataSource.user.local.dbo.UserDbo

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): UserDbo

    @Query("SELECT username FROM user")
    fun getUserName(): String

    @Query("SELECT token FROM user")
    fun getUserToken(): String

    @Query("SELECT id FROM user")
    fun getUserId(): Int

    @Query("SELECT COUNT(*) FROM user")
    fun insertUser(user: UserDbo)

    @Query("DELETE FROM user")
    fun deleteUser(user: UserDbo)

}