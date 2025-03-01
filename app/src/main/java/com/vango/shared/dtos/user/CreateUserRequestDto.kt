package com.vango.shared.dtos.user

data class CreateUserRequestDto(
    val email: String,
    val password: String,
    val typeLogIn: Int
)
