package com.vango.presentation.home

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@AndroidEntryPoint
class ActivityHome : ComponentActivity() {
    private lateinit var viewModel: ActivityHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[ActivityHomeViewModel::class]

        setContent {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var defaultLocation by remember {
        mutableStateOf(
            LatLng(
                40.416775,
                -3.703790
            )
        )

    } // Madrid

    val locationPermission = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    LaunchedEffect(locationPermission.status) {
        if (locationPermission.status.isGranted) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        defaultLocation = LatLng(location.latitude, location.longitude)
                        cameraPositionState.position =
                            CameraPosition.fromLatLngZoom(defaultLocation, 12f)
                    }
                }
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = com.google.maps.android.compose.MapProperties(
            isMyLocationEnabled = locationPermission.status.isGranted,
            isTrafficEnabled = true,
        ),
        uiSettings = com.google.maps.android.compose.MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
        ),
        content = {
            Marker(
                state = MarkerState(
                    position = LatLng(40.416775, -3.703790),
                ),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                title = "Madrid",
                snippet = "Capital of Spain"
            )
        }
    )

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}