package com.vango.domain.usecase

import com.vango.domain.respository.AuthRepository
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(private val authRepository: AuthRepository) :
    AuthUseCase {

    override suspend fun login(email: String, password: String): Boolean {
        return authRepository.login(email, password)
    }

    override fun validEmail(email: String): Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }

        if (email.matches("[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+".toRegex())) {
            return true
        }

        return false
    }

}
