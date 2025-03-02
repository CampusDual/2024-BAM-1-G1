package com.vango.data.dataSource.remote.user

import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import retrofit2.Response


interface UserRemoteDataSource {

    suspend fun createUser(authSignUpUserRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>

}