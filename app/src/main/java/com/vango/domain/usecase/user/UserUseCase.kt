package com.vango.domain.usecase.user

interface UserUseCase {
    suspend fun createUser(email: String, password: String, typeLogIn: Int): Result<String?>
    suspend fun saveProfile(firebaseId: String, profileNick: String, profileAge: Int, profileCountry: Int, profileProvince: Int) : Result<String?>
    suspend fun getProfile(firebaseId: String): Result<String?>
}