package com.vango.data.dataSource.remote.auth

import com.vango.data.dataSource.remote.auth.dto.UserDto

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): Boolean
    suspend fun signUp(dto: UserDto): Pair<Boolean, String>
    suspend fun getUser(): List<String>
    fun logout()
}
