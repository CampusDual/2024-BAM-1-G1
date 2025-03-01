package com.vango.data.repository

import com.vango.data.dataSource.remote.user.UserRemoteDataSourceImpl
import com.vango.domain.respositories.UserRepository
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSourceImpl: UserRemoteDataSourceImpl
) : UserRepository
{

    override suspend fun createUser(userRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
    {
        return userRemoteDataSourceImpl.createUser(userRequestDto)
    }
}