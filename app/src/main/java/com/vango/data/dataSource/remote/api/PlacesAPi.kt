package com.vango.data.dataSource.remote.api

import com.vango.shared.dtos.places.PlacesResponseDto
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PlacesAPi {

    @GET("api/Places/nearby")
    suspend fun getPlacesNearby(
        @QueryMap parameters: Map<String, String>
    ): List<PlacesResponseDto>

}