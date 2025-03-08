package com.vango.presentation.main.home

import android.Manifest
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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
    navController: NavHostController,
    cameraPositionState: CameraPositionState? = null,
    permissionState: PermissionState? = null,
    isPreview: Boolean = false
) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var defaultLocation by remember {
        mutableStateOf(LatLng(40.416775, -3.703790)) // Madrid
    }

    val locationPermission =
        permissionState ?: rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
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

    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
        .build()

    val locationListener = LocationListener { location ->
        val newLocation = LatLng(location.latitude, location.longitude)
        cameraState.position = CameraPosition.fromLatLngZoom(newLocation, 15f)
    }

    fusedLocationClient.requestLocationUpdates(locationRequest, locationListener, null)



    fun moveYourLocation() {
        if (locationPermission.status.isGranted) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val newLocation = LatLng(location.latitude, location.longitude)
                        defaultLocation = newLocation
                        cameraState.position = CameraPosition.fromLatLngZoom(newLocation, 15f)
                    }
                }
        } else {
            locationPermission.launchPermissionRequest()
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
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
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

            Button(
                onClick = { navController.navigate("results")},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentWidth()
                    .padding(16.dp)
            ) {
                Text("Lista")
            }

            // Button to navigate to the results screen
            Button(
                onClick = { moveYourLocation() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .wrapContentWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Stop"
                )
            }
            // button to add a new location
            Button(
                onClick = { navController.navigate("results") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .wrapContentWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }


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
    val navController = rememberNavController()
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(40.416775, -3.703790), 12f)
    }

    HomeScreen(
        cameraPositionState = cameraState,
        permissionState = mockPermissionState,
        navController = navController
    )
}