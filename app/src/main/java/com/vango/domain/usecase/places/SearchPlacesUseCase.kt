package com.vango.domain.usecase.places

import com.google.android.gms.maps.model.LatLng
import com.vango.data.repository.PlacesRepository
import com.vango.data.repository.SearchRepository
import com.vango.domain.model.SearchResult
import com.vango.shared.dtos.places.PlacesRequestDto
import com.vango.shared.dtos.places.PlacesResponseDto
import javax.inject.Inject

class SearchPlacesUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val placesRepository: PlacesRepository
) {
    suspend operator fun invoke(query: String, currentLocation: LatLng): List<SearchResult> {
        return searchRepository.searchPlaces(query, currentLocation)
    }

    suspend fun getPlaceDetails(placeId: String): SearchResult {
        return searchRepository.getPlaceDetails(placeId)
    }

    suspend fun searchNearby(request: PlacesRequestDto): List<PlacesResponseDto> {
        return placesRepository.getPlacesNearby(request)
    }

    fun toLatLng(result: SearchResult): LatLng {
        return LatLng(result.latitude, result.longitude)
    }


}