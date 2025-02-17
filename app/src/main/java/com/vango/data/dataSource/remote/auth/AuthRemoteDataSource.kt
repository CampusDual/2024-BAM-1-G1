package com.vango.data.dataSource.remote.auth

import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse

interface AuthRemoteDataSource {
    suspend fun login(authDto: AuthDtoRequest): AuthDtoResponse
}