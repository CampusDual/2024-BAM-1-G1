package com.vango.domain.respositories

import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.user.userCompleteProfileRequestDto
import com.vango.shared.dtos.user.userCompleteProfileResponseDto
import retrofit2.Response

interface UserRepository {
    suspend fun createUser(userRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
    suspend fun saveProfile(userRequestDto: userCompleteProfileRequestDto): Response<userCompleteProfileResponseDto>
    suspend fun getProfile(firebaseId: String): Response<userCompleteProfileResponseDto>
}