package com.vango.domain.usecase.location

import com.google.android.gms.maps.model.LatLng
import com.vango.data.repository.LocationRepositoryImpl
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepositoryImpl
) {
    suspend operator fun invoke(): LatLng? {
        return try {
            locationRepository.getCurrentLocation()
        } catch (e: SecurityException) {
            null
        }
    }
}