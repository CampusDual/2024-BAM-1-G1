package com.vango.shared.dtos.places

import com.google.android.gms.maps.model.LatLng

data class PlacesResponseDto(
    val title: String?,
    val address: String?,
    val location: String?,
    val rating: Float?,
    val userVotes: Int?,
    val photoUrls: List<String>?,
    val type: Int?,
    val placeId: String?,
    val currentOpeningHours: Any?,
    val opening_hours: Any?,
    val international_phone_number: String?,
    val formattedPhoneNumber: String?,
    val website: String?,
    val reviews: Any?,
    val source: Int?
) {
    fun toLatLng(): LatLng? {
        return try {
            location?.split(",")?.let { coordinates ->
                if (coordinates.size == 2) {
                    val lat = coordinates[0].trim().toDouble()
                    val lng = coordinates[1].trim().toDouble()
                    LatLng(lat, lng)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("PlacesResponseDto", "Error parsing location: $location", e)
            null
        }
    }

}