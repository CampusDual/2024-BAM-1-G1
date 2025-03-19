package com.vango.shared.dtos.auth

data class AuthDtoResponseDto(
    val uuid: String? = null,
    val kind: String? = null,
    val localId: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val idToken: String? = null,
    val registered: Boolean? = null,
    val refreshToken: String? = null,
    val expiresIn: String? = null
) {

}