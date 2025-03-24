package com.vango.shared.dtos.user

data class userCompleteProfileRequestDto (
    val firebaseId: String,
    val profileNick: String,
    val profileAge: Int,
    val profileCountry: Int,
    val profileProvince: Int
)