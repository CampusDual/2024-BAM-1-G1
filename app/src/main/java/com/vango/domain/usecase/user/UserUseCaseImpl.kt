package com.vango.domain.usecase.user

import com.vango.domain.entities.User
import com.vango.domain.respositories.UserRepository
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : UserUseCase
{
    override suspend fun createUser(email: String, password: String, typeLogIn: Int): Result<String?> {
        val createUserRequestDto = CreateUserRequestDto(email, password, typeLogIn)
        val result = userRepository.createUser(createUserRequestDto)

        return if (result.isSuccessful) {
            Result.success(result.body()?.firebaseId)
        } else {
            Result.failure(Exception("Error al crear usuario: ${result.code()} - ${result.errorBody()?.string()}"))
        }
    }

}