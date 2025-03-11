package com.vango.domain.model

data class SearchResult(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val placeId: String? = null,
    val secondaryText: String? = null,
    val distanceMeters: Int? = null
)