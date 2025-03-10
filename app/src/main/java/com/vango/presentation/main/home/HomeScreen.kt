package com.vango.presentation.main.home

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import com.vango.presentation.main.home.components.BottomActionButtons
import com.vango.presentation.main.home.components.FilterMenu
import com.vango.presentation.main.home.components.LocationActionButtons
import com.vango.presentation.main.home.components.MapComponent
import com.vango.presentation.main.home.components.SearchBar
import com.vango.presentation.main.home.components.TopCenterButton

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    isPreview: Boolean = false
) {
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val currentLocation = viewModel.currentLocation.collectAsState().value
    val searchQuery = viewModel.searchQuery.collectAsState().value
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 12f)
    }

    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    LaunchedEffect(locationPermission.status) {
        if (locationPermission.status.isGranted && !isPreview) {
            viewModel.fetchUserLocation()
        }
    }

    LaunchedEffect(currentLocation) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
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
            MapComponent(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                currentLocation = currentLocation,
                isLocationEnabled = locationPermission.status.isGranted
            )

            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::updateSearchQuery,
                performSearch = viewModel::performSearch,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(start = 20.dp, end = 20.dp)
            )

            LocationActionButtons(
                onMoveToLocation = { viewModel.fetchUserLocation() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp, top = 120.dp)
            )

            FilterMenu(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = 120.dp, x = 20.dp)
            )

            TopCenterButton(
                onNavigateToResults = { navController.navigate("results") },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 120.dp, start = 5.5.dp)
            )

            BottomActionButtons(
                onNavigateToResults = { navController.navigate("results") },
                onAddAction = {  },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(
        navController = navController,
        isPreview = true
    )
}