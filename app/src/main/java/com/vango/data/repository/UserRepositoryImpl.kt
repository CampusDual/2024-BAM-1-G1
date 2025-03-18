package com.vango.data.repository

import com.vango.data.dataSource.remote.user.UserRemoteDataSourceImpl
import com.vango.domain.respositories.UserRepository
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
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
}