package com.vango.presentation.main.home

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState? = null,
    permissionState: PermissionState? = null,
    isPreview: Boolean = false
) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var defaultLocation by remember {
        mutableStateOf(LatLng(40.416775, -3.703790)) // Madrid
    }

    val locationPermission = permissionState ?: rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val cameraState = cameraPositionState ?: rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    LaunchedEffect(locationPermission.status) {
        if (locationPermission.status.isGranted && !isPreview) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        defaultLocation = LatLng(location.latitude, location.longitude)
                        cameraState.position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
                    }
                }
        }
    }

    if (isPreview) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Google Map Placeholder\nMadrid (40.416775, -3.703790)")
        }
    } else {
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermission.status.isGranted,
                isTrafficEnabled = true,
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = false,
                zoomControlsEnabled = false,
            )
        ) {
            Marker(
                state = MarkerState(position = LatLng(40.416775, -3.703790)),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                title = "Madrid",
                snippet = "Capital of Spain"
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockPermissionState = object : PermissionState {
        override val permission: String
            get() = Manifest.permission.ACCESS_FINE_LOCATION
        override val status: com.google.accompanist.permissions.PermissionStatus
            get() = com.google.accompanist.permissions.PermissionStatus.Granted
        override fun launchPermissionRequest() {}
    }

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.416775, -3.703790), 12f)
    }

    HomeScreen(
        cameraPositionState = cameraState,
        permissionState = mockPermissionState
    )
}