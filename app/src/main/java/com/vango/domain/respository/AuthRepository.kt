package com.vango.domain.respository

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean

}