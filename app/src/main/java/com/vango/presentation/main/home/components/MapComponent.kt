package com.vango.presentation.main.home.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng,
    isLocationEnabled: Boolean
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = isLocationEnabled,
            isTrafficEnabled = true
        ),
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false
        )
    ) {
        Marker(
            state = MarkerState(position = currentLocation),
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
            title = "Current Location",
            snippet = "Your location"
        )
    }
}