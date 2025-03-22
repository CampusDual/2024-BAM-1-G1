package com.vango.data.repository

import com.vango.data.dataSource.remote.api.PlacesAPi
import com.vango.shared.dtos.places.PlacesRequestDto
import com.vango.shared.dtos.places.PlacesResponseDto
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val placesApi: PlacesAPi
) : PlacesRepository {
    override suspend fun getPlacesNearby(request: PlacesRequestDto): List<PlacesResponseDto> {
        return placesApi.getPlacesNearby(request.toQueryMap())
    }
}


interface PlacesRepository {
    suspend fun getPlacesNearby(request: PlacesRequestDto): List<PlacesResponseDto>
}