package com.vango.data.dataSource.remote.auth

import com.vango.shared.dtos.auth.AuthDtoRequestDto
import com.vango.shared.dtos.auth.AuthDtoResponseDto
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import com.vango.shared.dtos.auth.AuthWhitTokenRequestDto
import com.vango.shared.dtos.auth.AuthWhitTokenResponseDto
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun getUser(): List<String>
    fun logout()
    suspend fun logIn(userLoginDto: AuthDtoRequestDto): Result<AuthDtoResponseDto>
    suspend fun recoverPassword(email: String): Result<Boolean>
    suspend fun signUp(authSignUpUserRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
    suspend fun verifyUserEmail(verifyUserEmailRequestDto: AuthVerifyUserEmailUpUserRequestDto): Response<AuthVerifyUserEmailUpUserResponseDto>
    suspend fun logInWhitToken(userLoginWhitTokenDto: AuthWhitTokenRequestDto): Result<AuthWhitTokenResponseDto>
}