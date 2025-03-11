package com.vango.presentation.main.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng,
    isLocationEnabled: Boolean,
    selectedLayer: MapLayer = MapLayer.NORMAL,
    onLocationVisibilityChanged: (Boolean) -> Unit = {}
) {
    val mapProperties = remember(selectedLayer) {
        MapProperties(
            mapType = when (selectedLayer) {
                MapLayer.NORMAL -> MapType.NORMAL
                MapLayer.SATELLITE -> MapType.SATELLITE
                MapLayer.RELIEF -> MapType.TERRAIN
                MapLayer.NO_CONNECTION -> MapType.NONE
            }
        )
    }

    val mapUiSettings = remember {
        MapUiSettings(
            compassEnabled = true,
            zoomControlsEnabled = false
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings,
        onMapLoaded = {
            val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
            bounds?.let {
                onLocationVisibilityChanged(it.contains(currentLocation))
            }
        }
    ) {
        if (isLocationEnabled) {
            Marker(
                state = MarkerState(position = currentLocation),
                title = "Ubicación actual"
            )
        }
    }

    LaunchedEffect(cameraPositionState.position) {
        val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
        bounds?.let {
            onLocationVisibilityChanged(it.contains(currentLocation))
        }
    }
}