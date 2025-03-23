package com.vango.shared.dtos.user

import com.google.firebase.firestore.Exclude

data class UserDto(
    @Exclude
    var uuid: String?,
    val email: String,
    val password: String,
)
