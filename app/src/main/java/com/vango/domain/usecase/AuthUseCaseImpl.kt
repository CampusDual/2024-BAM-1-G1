package com.vango.domain.usecase

import android.util.Log
import com.vango.domain.respository.AuthRepository

import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    AuthUseCase {

    override suspend fun login(email: String, password: String): Boolean {
        return authRepository.login(email, password)
    }

    override fun validEmail(email: String): Pair<Boolean, String> {
        if (email.isNullOrEmpty()) return Pair(false, "Email vacio")

        if (email.matches("[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+".toRegex())) return Pair(true, "")

        return Pair(false, "Email invalido")
    }

    override fun validPassword(password: String): Pair<Boolean, String> {
        if (password.isNullOrEmpty()) return Pair(false, "Contraseña vacia")
        if (password.length < 6) return Pair(false, "Contraseña muy corta")
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[#%^*+=_¿¡?=.*\\[/:()&@?!]).{6,}$".toRegex())) return Pair(false, "Deve contener mayusculas minusculas y caracteres especiales")
        return Pair(true, "")

    }

    override fun validConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String> {
        if (password != confirmPassword) return Pair(false, "Contraseñas no coinciden")
        return Pair(true, "")
    }

}
