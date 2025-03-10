package com.vango.domain.model

import java.util.Date

data class User(
    var firebaseId: String?,
    val name: String?,
    val lastName: String?,
    val email: String?,
    val address : String?,
    val phoneNumber : String?,
    val birthDateTime : Date?,
    val genderId : Int?,
    val countryId : Int?,
    val provinceId : Int?,
    val imageId : Int?,
) {
}