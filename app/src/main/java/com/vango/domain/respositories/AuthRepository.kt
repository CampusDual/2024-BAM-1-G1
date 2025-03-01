package com.vango.domain.respositories

import com.vango.shared.dtos.AuthDtoResponse
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response

interface AuthRepository {


    suspend fun logIn(email: String, password: String): Result<AuthDtoResponse>
    suspend fun recoverPassword(email: String): Result<Boolean>
    suspend fun signUp(userRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
}