package com.vango.domain.usecase

import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse

interface AuthUseCase {

    fun validEmail(email: String) : Pair<Boolean, String>
    fun validPassword(password: String) : Pair<Boolean, String>
    fun validConfirmPassword(password: String, confirmPassword: String) : Pair<Boolean, String>
    suspend fun signUp(email: String, password: String, confirmPassword: String) : Boolean


    suspend fun logIn(email: String, password: String): Result<AuthDtoResponse>
    suspend fun recoverPassword(email: String): Result<Boolean>
}