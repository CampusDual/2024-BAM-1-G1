package com.vango.data.dataSource.remote.api

import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("api/Auth/signup")
    suspend fun createUser(
        @Body createUserRequestDto: CreateUserRequestDto
    ): Response<CreateUserResponseDto>

}