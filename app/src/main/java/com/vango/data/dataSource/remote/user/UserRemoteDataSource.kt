package com.vango.data.dataSource.remote.user

import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response


interface UserRemoteDataSource {

    suspend fun createUser(createUserRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>

}