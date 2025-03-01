package com.vango.data.dataSource.remote.api

import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("api/Auth/signup")
    suspend fun signUp(
        @Body authSignUpUserRequestDto: AuthSignUpUserRequestDto
    ): Response<AuthSignUpUserResponseDto>

    @POST("api/Auth/verify-code")
    suspend fun verifyUserEmail(
        @Body authVerifyUserEmailUpUserRequestDto: AuthVerifyUserEmailUpUserRequestDto
    ): Response<AuthVerifyUserEmailUpUserResponseDto>

}