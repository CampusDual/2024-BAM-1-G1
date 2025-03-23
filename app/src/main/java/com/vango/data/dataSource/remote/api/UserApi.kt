package com.vango.data.dataSource.remote.api

import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import com.vango.shared.dtos.user.userCompleteProfileRequestDto
import com.vango.shared.dtos.user.userCompleteProfileResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface UserApi {

    @POST("api/Auth/signup")
    suspend fun signUp(
        @Body authSignUpUserRequestDto: AuthSignUpUserRequestDto
    ): Response<AuthSignUpUserResponseDto>

    @POST("api/Auth/verify-code")
    suspend fun verifyUserEmail(
        @Body authVerifyUserEmailUpUserRequestDto: AuthVerifyUserEmailUpUserRequestDto
    ): Response<AuthVerifyUserEmailUpUserResponseDto>

    @POST("api/User/completed-perfil")
    suspend fun SaveProfile(
        @Body userCompleteProfileRequestDto: userCompleteProfileRequestDto
    ): Response<userCompleteProfileResponseDto>

    @GET("api/User/get-user")
    suspend fun getUser(
        @QueryMap parameters: Map<String, String>
    ): Response<userCompleteProfileResponseDto>

}