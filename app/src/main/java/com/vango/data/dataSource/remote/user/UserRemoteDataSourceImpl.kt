package com.vango.data.dataSource.remote.user

import com.vango.data.dataSource.remote.api.UserApi
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.user.userCompleteProfileRequestDto
import com.vango.shared.dtos.user.userCompleteProfileResponseDto
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) :
    UserRemoteDataSource {

    override suspend fun createUser(authSignUpUserRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto> {
        return userApi.signUp(authSignUpUserRequestDto)
    }

    override suspend fun saveProfile(userCompleteProfileRequestDto: userCompleteProfileRequestDto): Response<userCompleteProfileResponseDto> {

        return userApi.SaveProfile(userCompleteProfileRequestDto)
    }

    override suspend fun getProfile(firebaseId: String): Response<userCompleteProfileResponseDto> {
        return userApi.getUser(mapOf("firebaseId" to firebaseId))
    }
}