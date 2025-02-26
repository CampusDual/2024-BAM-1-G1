package com.vango.data.dataSource.remote.auth.dto

import com.google.firebase.firestore.Exclude

data class UserDto(
    @Exclude
    var uuid: String?,
    val email: String,
    val password: String,
)
