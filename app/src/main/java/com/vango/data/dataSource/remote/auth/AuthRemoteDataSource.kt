package com.vango.data.dataSource.remote.auth

import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse
import com.vango.data.dataSource.remote.auth.dto.UserDto

interface AuthRemoteDataSource {
    suspend fun login(authDto: AuthDtoRequest): AuthDtoResponse
    suspend fun signUp(dto: UserDto): Pair<Boolean, String>
    suspend fun getUser(): List<String>
    fun logout()
}