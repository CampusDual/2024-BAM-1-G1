package com.vango.domain.usecase.auth

import com.vango.shared.dtos.auth.AuthDtoResponseDto

interface AuthUseCase {

    fun validEmail(email: String) : Pair<Boolean, String>
    fun validPassword(password: String) : Pair<Boolean, String>
    fun validConfirmPassword(password: String, confirmPassword: String) : Pair<Boolean, String>
    suspend fun logIn(email: String, password: String): Result<AuthDtoResponseDto>
    suspend fun recoverPassword(email: String): Result<Boolean>
    suspend fun signUp(email: String, password: String, typeLogIn: Int): Result<String?>
    suspend fun verifyUserEmail(firebaseId: String, verificationCode: String): Result<Boolean>
}