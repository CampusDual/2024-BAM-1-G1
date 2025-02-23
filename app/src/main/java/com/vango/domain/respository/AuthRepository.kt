package com.vango.domain.respository

import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse

interface AuthRepository {

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun logIn(email: String, password: String): Result<AuthDtoResponse>
    suspend fun recoverPassword(email: String): Result<Boolean>
}