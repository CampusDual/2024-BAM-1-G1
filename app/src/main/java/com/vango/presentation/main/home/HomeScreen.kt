package com.vango.presentation.main.home

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.vango.R
import com.vango.presentation.main.home.components.BottomActionButtons
import com.vango.presentation.main.home.components.FilterMenu
import com.vango.presentation.main.home.components.LocationActionButtons
import com.vango.presentation.main.home.components.MapComponent
import com.vango.presentation.main.home.components.SearchBar
import com.vango.presentation.main.home.components.TopCenterButton
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorBadge
import com.vango.presentation.theme.BackgroundColorButtonPrincipal
import com.vango.presentation.theme.BackgroundColorList

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
                    .padding(top = 48.dp, start = 20.dp, end = 20.dp)
            )

            LocationActionButtons(
                onMoveToLocation = { viewModel.fetchUserLocation() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp, top = 120.dp)
            )

            FilterMenu(
                onMoveYourLocation = { viewModel.fetchUserLocation() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 120.dp, start = 20.dp)
            )

            TopCenterButton(
                onNavigateToResults = { navController.navigate("results") },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 120.dp, start = 5.5.dp)
            )

            BottomActionButtons(
                onNavigateToResults = { navController.navigate("results") },
                onAddAction = { /* Lógica para agregar */ },
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