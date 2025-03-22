package com.vango.domain.model

data class Place(
    val title: String,
    val address: String,
    val location : String,
    val lat: Double,
    val lng: Double,
    val rating: Float,
    val userVotes: Int,
    val photoUrls: List<String>,
    val type: Int,
    val placeId: String,
    val source: Int
) {

}