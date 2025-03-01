package com.vango.domain.respositories

import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response

interface UserRepository {
    suspend fun createUser(userRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
}