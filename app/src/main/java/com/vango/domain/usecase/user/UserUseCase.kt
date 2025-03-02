package com.vango.domain.usecase.user

interface UserUseCase {
    suspend fun createUser(email: String, password: String, typeLogIn: Int): Result<String?>
}