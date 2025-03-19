package com.vango.shared.dtos.places

data class PlacesRequestDto(
    val lat: Double,
    val lng: Double,
    val radius: Int,
    val placeType: Int,
) {
    fun toQueryMap(): Map<String, String> = mapOf(
        "lat" to lat.toString(),
        "long" to lng.toString(),
        "radius" to radius.toString(),
        "placeType" to placeType.toString()
    )
}