package com.vango.presentation.main.home.components

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.vango.R


@Composable
fun MapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng,
    isLocationEnabled: Boolean,
    selectedLayer: MapLayer = MapLayer.NORMAL,
    onLocationVisibilityChanged: (Boolean) -> Unit = {},
    onMapClick: (LatLng) -> Unit,
    isSelectingPoint: Boolean = false,
    isShowingRoutePoint: Boolean = false

) {
    val mapProperties = remember(selectedLayer) {
        MapProperties(
            mapType = when (selectedLayer) {
                MapLayer.NORMAL -> MapType.NORMAL
                MapLayer.SATELLITE -> MapType.SATELLITE
                MapLayer.RELIEF -> MapType.TERRAIN
                MapLayer.NO_CONNECTION -> MapType.NONE
            },
            isMyLocationEnabled = isLocationEnabled
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
        },
        onMapClick = onMapClick
    ) {
        if (isLocationEnabled && currentLocation.latitude != 0.0 && currentLocation.longitude != 0.0) {

            if (!isSelectingPoint && !isShowingRoutePoint) {
                Marker(
                    state = MarkerState(position = currentLocation),
                    title = "Ubicación actual"
                )
            }


        }
        if (isSelectingPoint) {
            Marker(
                state = MarkerState(position = cameraPositionState.position.target),
                title = "Punto a seleccionar",
                snippet = "Toca el mapa para confirmar",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_plus)
            )
        }
        if (isShowingRoutePoint && !isSelectingPoint) {
            Marker(
                state = MarkerState(position = cameraPositionState.position.target),
                title = "Punto a seleccionar",
                snippet = "Toca el mapa para confirmar",
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