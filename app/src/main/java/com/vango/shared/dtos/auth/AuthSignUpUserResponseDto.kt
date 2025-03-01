package com.vango.shared.dtos.auth

data class AuthSignUpUserResponseDto(
    val firebaseId: String?,
    val name: String?,
    val lastName: String?,
    val email: String?,
    val isEmailVerified: Boolean?,
)
