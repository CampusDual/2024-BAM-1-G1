package com.vango.domain.usecase.places

import com.google.android.gms.maps.model.LatLng
import com.vango.data.repository.SearchRepository
import com.vango.domain.model.SearchResult
import javax.inject.Inject

class SearchPlacesUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, currentLocation: LatLng): List<SearchResult> {
        return searchRepository.searchPlaces(query, currentLocation)
    }

    suspend fun getPlaceDetails(placeId: String): SearchResult {
        return searchRepository.getPlaceDetails(placeId)
    }

    fun toLatLng(result: SearchResult): LatLng {
        return LatLng(result.latitude, result.longitude)
    }
}