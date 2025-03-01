package com.vango.data.dataSource.remote.auth

import com.vango.shared.dtos.AuthDtoRequest
import com.vango.shared.dtos.AuthDtoResponse
import com.vango.shared.dtos.UserDto
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun getUser(): List<String>
    fun logout()
    suspend fun logIn(userLoginDto: AuthDtoRequest): Result<AuthDtoResponse>
    suspend fun recoverPassword(email: String): Result<Boolean>
    suspend fun signUp(createUserRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
}