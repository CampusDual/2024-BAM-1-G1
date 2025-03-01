package com.vango.domain.respositories

import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import retrofit2.Response

interface UserRepository {
    suspend fun createUser(userRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
}