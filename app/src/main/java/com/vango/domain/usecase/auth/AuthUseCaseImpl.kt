package com.vango.domain.usecase.auth

import com.vango.domain.respositories.AuthRepository
import com.vango.shared.dtos.auth.AuthDtoResponseDto
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    AuthUseCase {

    override suspend fun logIn(email: String, password: String): Result<AuthDtoResponseDto> {
        return authRepository.logIn(email, password)
    }

    override suspend fun recoverPassword(email: String): Result<Boolean> {
        return authRepository.recoverPassword(email)
    }

    override fun validEmail(email: String): Pair<Boolean, String> {
        if (email.isNullOrEmpty()) return Pair(false, "Email vacio")

        if (email.matches("[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+".toRegex())) return Pair(true, "")

        return Pair(false, "Email invalido")
    }

    override fun validPassword(password: String): Pair<Boolean, String> {
        if (password.isNullOrEmpty()) return Pair(false, "Contraseña vacía")
        if (password.length < 6) return Pair(false, "Contraseña muy corta")
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[#%^*+=_¿¡?=.*\\[/:()&@?!]).{6,}$".toRegex())) return Pair(
            false,
            "Debe* contener mayúsculas minúsculas y caracteres especiales"
        )
        return Pair(true, "")

    }

    override fun validConfirmPassword(
        password: String,
        confirmPassword: String
    ): Pair<Boolean, String> {
        if (password != confirmPassword) return Pair(false, "Contraseñas no coinciden")
        return Pair(true, "")
    }

    override suspend fun signUp(email: String, password: String, typeLogIn: Int): Result<String?> {
        val authSignUpUserRequestDto = AuthSignUpUserRequestDto(email, password, typeLogIn)
        val result = authRepository.signUp(authSignUpUserRequestDto)

        return if (result.isSuccessful) {
            Result.success(result.body()?.firebaseId)
        } else {
            Result.failure(
                Exception(
                    "Error al crear usuario: ${result.code()} - ${
                        result.errorBody()?.string()
                    }"
                )
            )
        }
    }

    override suspend fun verifyUserEmail(
        firebaseId: String,
        verificationCode: String
    ): Result<Boolean> {
        val verifyUserEmailRequestDto =
            AuthVerifyUserEmailUpUserRequestDto(verificationCode, firebaseId)
        val result = authRepository.verifyUserEmail(verifyUserEmailRequestDto)

        val finalResult: Result<Boolean> = if (result.isSuccessful) {
            val token = result.body()?.token
            val login = authRepository.loginWhitToken(token.toString())

            if (login.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(
                    Exception(
                        "Error al iniciar sesión: ${login.code()} - ${
                            result.errorBody()?.string()
                        }"
                    )
                )
            }
        } else {
            Result.failure(
                Exception(
                    "Error al verificar usuario: ${result.code()} - ${
                        result.errorBody()?.string()
                    }"
                )
            )
        }

        return finalResult
    }

}
