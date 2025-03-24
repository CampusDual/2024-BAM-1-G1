package com.vango.data.dataSource.remote.user

import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.user.userCompleteProfileRequestDto
import com.vango.shared.dtos.user.userCompleteProfileResponseDto
import retrofit2.Response


interface UserRemoteDataSource {

    suspend fun createUser(authSignUpUserRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>

    suspend fun saveProfile(userCompleteProfileRequestDto: userCompleteProfileRequestDto): Response<userCompleteProfileResponseDto>

    suspend fun getProfile(firebaseId: String): Response<userCompleteProfileResponseDto>
}