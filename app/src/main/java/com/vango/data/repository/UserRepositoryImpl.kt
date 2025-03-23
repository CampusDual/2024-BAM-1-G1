package com.vango.data.repository

import com.vango.data.dataSource.remote.user.UserRemoteDataSourceImpl
import com.vango.domain.respositories.UserRepository
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.user.userCompleteProfileRequestDto
import com.vango.shared.dtos.user.userCompleteProfileResponseDto
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository
{
    override suspend fun createUser(userRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
    {
        return userRemoteDataSourceImpl.createUser(userRequestDto)
    }

    override suspend fun saveProfile(userRequestDto: userCompleteProfileRequestDto) : Response<userCompleteProfileResponseDto>
    {
        return userRemoteDataSourceImpl.saveProfile(userRequestDto)
    }

    override suspend fun getProfile(firebaseId: String): Response<userCompleteProfileResponseDto>
    {
        return userRemoteDataSourceImpl.getProfile(firebaseId)
    }
}