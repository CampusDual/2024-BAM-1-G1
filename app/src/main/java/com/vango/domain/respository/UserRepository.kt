package com.vango.domain.repository

import com.vango.data.dataSource.user.local.dbo.UserDbo

interface UserRepository {
    suspend fun insertUser(user: UserDbo)
    suspend fun getUser(): UserDbo?
    suspend fun updateUser(id: Int, token: String, username: String)
    suspend fun deleteUser(user: UserDbo)
    suspend fun deleteAllUsers()
}
