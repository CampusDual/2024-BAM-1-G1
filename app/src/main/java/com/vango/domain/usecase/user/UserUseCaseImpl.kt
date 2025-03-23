package com.vango.domain.usecase.user

import com.vango.domain.respositories.UserRepository
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.user.userCompleteProfileRequestDto
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : UserUseCase
{
    override suspend fun createUser(email: String, password: String, typeLogIn: Int): Result<String?> {
        val authSignUpUserRequestDto = AuthSignUpUserRequestDto(email, password, typeLogIn)
        val result = userRepository.createUser(authSignUpUserRequestDto)

        return if (result.isSuccessful) {
            Result.success(result.body()?.firebaseId)
        } else {
            Result.failure(Exception("Error al crear usuario: ${result.code()} - ${result.errorBody()?.string()}"))
        }
    }

    override suspend fun saveProfile(
        firebaseId: String,
        profileNick: String,
        profileAge: Int,
        profileCountry: Int,
        profileProvince: Int
    ): Result<String?> {
        val profileDto = userCompleteProfileRequestDto(firebaseId, profileNick, profileAge, profileCountry, profileProvince)
       var profile =  userRepository.saveProfile(profileDto)

        return if (profile.isSuccessful) {
            Result.success(profile.body()?.firebaseId)
        } else {
            Result.failure(Exception("Error al crear usuario: ${profile.code()} - ${profile.errorBody()?.string()}"))
        }
    }

    override suspend fun getProfile(firebaseId: String): Result<String?> {

        return try {
            val profile = userRepository.getProfile(firebaseId)
            if (profile.isSuccessful) {
               Result.success(profile.body()?.firebaseId)
            } else {
                Result.failure(Exception("Error al crear usuario: ${profile.code()} - ${profile.errorBody()?.string()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}