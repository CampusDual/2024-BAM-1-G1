package com.vango.data.dataSource.remote.user

import com.vango.data.dataSource.remote.api.UserApi
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {

    override suspend fun createUser(createUserRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
    {
        return userApi.createUser(createUserRequestDto)
    }


}