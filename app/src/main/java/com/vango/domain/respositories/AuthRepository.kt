package com.vango.domain.respositories

import com.vango.shared.dtos.auth.AuthDtoResponseDto
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import retrofit2.Response

interface AuthRepository {


    suspend fun logIn(email: String, password: String): Result<AuthDtoResponseDto>
    suspend fun recoverPassword(email: String): Result<Boolean>
    suspend fun signUp(userRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
    fun logout()
    suspend fun verifyUserEmail(verifyUserEmailRequestDto: AuthVerifyUserEmailUpUserRequestDto): Response<AuthVerifyUserEmailUpUserResponseDto>
}