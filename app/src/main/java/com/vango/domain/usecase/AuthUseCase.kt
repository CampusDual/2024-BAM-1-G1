package com.vango.domain.usecase

interface AuthUseCase {
    suspend fun login(email: String, password: String): Boolean
    fun validEmail(email: String) : Pair<Boolean, String>
    fun validPassword(password: String) : Pair<Boolean, String>
    fun validConfirmPassword(password: String, confirmPassword: String) : Pair<Boolean, String>
    suspend fun signUp(email: String, password: String, confirmPassword: String) : Boolean


}