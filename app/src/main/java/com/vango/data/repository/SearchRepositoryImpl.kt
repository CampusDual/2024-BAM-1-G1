package com.vango.data.repository

import com.google.android.gms.maps.model.LatLng
import com.vango.data.dataSource.remote.places.PlacesDataSource
import com.vango.domain.model.SearchResult
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val placesDataSource: PlacesDataSource
) : SearchRepository {
    override suspend fun searchPlaces(query: String, currentLocation: LatLng): List<SearchResult> {
        return placesDataSource.searchPlaces(query, currentLocation)
    }

    override suspend fun getPlaceDetails(placeId: String): SearchResult {
        return placesDataSource.getPlaceDetails(placeId)
    }
}

interface SearchRepository {
    suspend fun searchPlaces(query: String, currentLocation: LatLng): List<SearchResult>
    suspend fun getPlaceDetails(placeId: String): SearchResult
}