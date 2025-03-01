package com.vango.shared.dtos.user

data class CreateUserResponseDto(
    val firebaseId: String?,
    val name: String?,
    val lastName: String?,
    val email: String?,
    val isEmailVerified: Boolean?,
)
