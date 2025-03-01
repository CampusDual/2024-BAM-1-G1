package com.vango.data.dataSource.remote.user

import com.vango.data.dataSource.remote.api.UserApi
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {

    override suspend fun createUser(authSignUpUserRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
    {
        return userApi.signUp(authSignUpUserRequestDto)
    }


}