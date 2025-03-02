package com.vango.shared.dtos.auth

data class AuthVerifyUserEmailUpUserResponseDto(
    val firebaseId: String,
    val token: String,
)
