package com.vango.shared.dtos.auth

data class AuthSignUpUserRequestDto(
    val email: String,
    val password: String,
    val typeLogIn: Int
)
