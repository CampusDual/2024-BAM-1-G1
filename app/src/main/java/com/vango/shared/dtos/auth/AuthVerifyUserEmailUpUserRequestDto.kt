package com.vango.shared.dtos.auth

data class AuthVerifyUserEmailUpUserRequestDto(
    val verificationCode: String,
    val firebaseId: String,
)
