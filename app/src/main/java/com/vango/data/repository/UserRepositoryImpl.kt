package com.vango.data.repository

import com.vango.data.dataSource.user.local.UserDao
import com.vango.data.dataSource.user.local.dbo.UserDbo
import com.vango.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: UserDbo) {
        userDao.insertUser(user)
    }

    override suspend fun getUser(): UserDbo? {
        return userDao.getUser()
    }

    override suspend fun updateUser(id: Int, token: String, username: String) {
        userDao.updateUser(id, token, username)
    }

    override suspend fun deleteUser(user: UserDbo) {
        userDao.deleteUser(user)
    }

}
