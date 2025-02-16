package com.vango.domain.usecase

interface AuthUseCase {
    suspend fun login(email: String, password: String): Boolean
    fun validEmail(email: String) : Boolean
    fun validPassword(password: String) : Boolean
}