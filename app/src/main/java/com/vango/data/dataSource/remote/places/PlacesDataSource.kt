package com.vango.data.dataSource.remote.places

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.vango.domain.model.SearchResult
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class PlacesDataSource @Inject constructor(
    private val placesClient: PlacesClient
) {

        suspend fun searchPlaces(query: String, currentLocation: com.google.android.gms.maps.model.LatLng): List<SearchResult> = suspendCancellableCoroutine { continuation ->
            try {
                val placesLatLng = LatLng(
                    currentLocation.latitude,
                    currentLocation.longitude
                )

                val circle = CircularBounds.newInstance(placesLatLng,  50000.0)



                val distance = 50.0
                val latDelta = distance / 111.0
                val lonDelta = distance / (111.0 * Math.cos(Math.toRadians(currentLocation.latitude)))

                val southwest = LatLng(
                    currentLocation.latitude - latDelta,
                    currentLocation.longitude - lonDelta
                )
                val northeast = LatLng(
                    currentLocation.latitude + latDelta,
                    currentLocation.longitude + lonDelta
                )

                val bounds = RectangularBounds.newInstance(southwest, northeast)

                val request = FindAutocompletePredictionsRequest.builder()
                    .setQuery(query)
                    .setOrigin(placesLatLng)
                    .setCountries("ES")
                    .build()

                Log.d("PlacesDataSource", "Current Location: Lat=${currentLocation.latitude}, Lng=${currentLocation.longitude}")

                placesClient.findAutocompletePredictions(request)
                    .addOnSuccessListener { response ->
                        val results = response.autocompletePredictions
                            .map { prediction ->
                                SearchResult(
                                    name = prediction.getPrimaryText(null).toString(),
                                    latitude = 0.0,
                                    longitude = 0.0,
                                    placeId = prediction.placeId,
                                    secondaryText = prediction.getSecondaryText(null).toString(),
                                    distanceMeters = prediction.distanceMeters
                                )
                            }
                            .sortedBy { it.distanceMeters?.toDouble() ?: Double.MAX_VALUE }

                        Log.d("PlacesDataSource", "Resultados ordenados: ${results.map { "${it.name} (${it.distanceMeters} m)" }}")
                        continuation.resume(results)
                    }
                    .addOnFailureListener { exception ->
                        Log.e("PlacesDataSource", "Error en findAutocompletePredictions: ${exception.message}", exception)
                        continuation.resumeWithException(exception)
                    }
            } catch (e: Exception) {
                Log.e("PlacesDataSource", "Excepción en searchPlaces: ${e.message}", e)
                continuation.resumeWithException(e)
            }
        }



    suspend fun getPlaceDetails(placeId: String): SearchResult = suspendCancellableCoroutine { continuation ->
        val request = com.google.android.libraries.places.api.net.FetchPlaceRequest.builder(
            placeId,
            listOf(Place.Field.DISPLAY_NAME, Place.Field.LOCATION)
        ).build()

        placesClient.fetchPlace(request)
            .addOnSuccessListener { response ->
                val place = response.place
                val result = SearchResult(
                    name = place.displayName ?: "Unknown",
                    latitude = place.location?.latitude ?: 0.0,
                    longitude = place.location?.longitude ?: 0.0
                )
                continuation.resume(result)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}