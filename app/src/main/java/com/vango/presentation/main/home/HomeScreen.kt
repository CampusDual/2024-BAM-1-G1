package com.vango.presentation.main.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState
import com.vango.presentation.main.home.components.BottomActionButtons
import com.vango.presentation.main.home.components.BottomCoordButton
import com.vango.presentation.main.home.components.FilterMenu
import com.vango.presentation.main.home.components.LocationActionButtons
import com.vango.presentation.main.home.components.MapComponent
import com.vango.presentation.main.home.components.MapLayersMenu
import com.vango.presentation.main.home.components.MapNewPointMenu
import com.vango.presentation.main.home.components.MapNewPointNameMenu
import com.vango.presentation.main.home.components.MapNewPointRoute
import com.vango.presentation.main.home.components.MapNewPointTagMenu
import com.vango.presentation.main.home.components.MapNewPointTagServicesMenu
import com.vango.presentation.main.home.components.MapNewRoutePointMenu
import com.vango.presentation.main.home.components.SearchBar
import com.vango.presentation.main.home.components.TopCenterButton
import com.vango.shared.dtos.places.PlacesResponseDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    isPreview: Boolean = false,
    onMapLayersMenuVisibilityChange: (Boolean) -> Unit = {}
) {
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val currentLocation by viewModel.currentLocation.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }
    var showMapLayersMenu by remember { mutableStateOf(false) }
    val selectedLayer by viewModel.selectedLayer.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val hasToRequestPermission by viewModel.hasToRequestPermission.collectAsState()
    var isLocationVisible by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    val selectedRoutePoint by viewModel.selectedRoutePoint.collectAsState()
    var showMapCreatePointRouteMenu by remember { mutableStateOf(false) }

    var showBottomActionButtons by remember { mutableStateOf(true) }
    var isSelectingPoint by remember { mutableStateOf(false) }
    var showMapNewPointMenu by remember { mutableStateOf(false) }
    var showMapNewPointNameMenu by remember { mutableStateOf(false) }
    var showMapNewPointTagMenu by remember { mutableStateOf(false) }
    var showMapNewPointTagServicesMenu by remember { mutableStateOf(false) }
    var isMapLoaded by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<PlacesResponseDto?>(null) }

    val nearbyPlaces by viewModel.nearbyPlaces.collectAsState()

    LaunchedEffect(Unit) {
        locationPermission.launchPermissionRequest()
    }

    LaunchedEffect(locationPermission.status) {
        if (locationPermission.status.isGranted && !isPreview) {
            viewModel.fetchUserLocation()
        }
    }

    LaunchedEffect(currentLocation) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f), 1000)

    }

    LaunchedEffect(isMapLoaded, currentLocation) {
        if (isMapLoaded && currentLocation != LatLng(40.416775, -3.703790) && nearbyPlaces.isEmpty()) {
            viewModel.searchNearbyPlaces(radius = 5000, placeType = 5, latLng = currentLocation)
        }
    }

    LaunchedEffect(nearbyPlaces) {
        if (nearbyPlaces.isNotEmpty()) {
            val boundsBuilder = LatLngBounds.Builder()
            nearbyPlaces.forEach { place ->
                place.toLatLng()?.let { boundsBuilder.include(it) }
            }
            boundsBuilder.include(currentLocation)
            val bounds = boundsBuilder.build()
            cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(bounds, 100), 1000)
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.clearErrorMessage()
        }
    }

    LaunchedEffect(hasToRequestPermission) {
        if (hasToRequestPermission && !locationPermission.status.isGranted) {
            if (locationPermission.status.shouldShowRationale) {
                locationPermission.launchPermissionRequest()
            } else {
                showPermissionDialog = true
            }
            viewModel.clearPermissionRequest()
        }
    }

    LaunchedEffect(showMapLayersMenu) {
        onMapLayersMenuVisibilityChange(showMapLayersMenu)
    }
    LaunchedEffect(showMapCreatePointRouteMenu) {
        onMapLayersMenuVisibilityChange(showMapCreatePointRouteMenu)
    }
    LaunchedEffect(showMapNewPointMenu) {
        onMapLayersMenuVisibilityChange(showMapNewPointMenu)
    }
    LaunchedEffect(showMapNewPointNameMenu) {
        onMapLayersMenuVisibilityChange(showMapNewPointNameMenu)
    }
    LaunchedEffect(showMapNewPointTagMenu) {
        onMapLayersMenuVisibilityChange(showMapNewPointTagMenu)
    }
    LaunchedEffect(showMapNewPointTagServicesMenu) {
        onMapLayersMenuVisibilityChange(showMapNewPointTagServicesMenu)
    }

    LaunchedEffect(viewModel.selectedPoint.collectAsState().value) {
        viewModel.selectedPoint.value?.let { latLng ->
            val address = viewModel.selectedAddress.value ?: "No address available"
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Permisos necesarios") },
            text = { Text("Por favor, habilita los permisos de ubicación en la configuración de la app para continuar.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPermissionDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
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
                isLocationEnabled = locationPermission.status.isGranted,
                selectedLayer = selectedLayer,
                nearbyPlaces = nearbyPlaces,
                onLocationVisibilityChanged = { visible ->
                    isLocationVisible = visible
                },
                isSelectingPoint = isSelectingPoint,
                isShowingRoutePoint = showMapNewPointMenu,
                onMapClick = {
                    if (isSelectingPoint) {
                        val centerLatLng = cameraPositionState.position.target
                        viewModel.selectPoint(centerLatLng)
                        isSelectingPoint = false
                        showBottomActionButtons = true
                        showMapNewPointMenu = true
                    }
                },
                onMapLoadedCallback = {
                    isMapLoaded = true
                },
                onPlaceSelected = { place ->
                    selectedPlace = place
                }

            )

            LocationActionButtons(
                onMoveToLocation = {
                    scope.launch {
                        viewModel.fetchUserLocation()
                        delay(100)
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLocation,
                                15f
                            ), 1000
                        )
                    }
                },
                onMapLayerClick = { showMapLayersMenu = true },
                selectedOption = selectedOption,
                isLocationVisible = isLocationVisible,
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
                viewModel = viewModel,
                cameraPositionState = cameraPositionState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 120.dp, start = 5.5.dp)
            )

            if (showBottomActionButtons && !isSelectingPoint && selectedPlace == null) {
                BottomActionButtons(
                    onNavigateToResults = { navController.navigate("results") },
                    onAddAction = {
                        showBottomActionButtons = false
                        showMapCreatePointRouteMenu = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 101.dp)
                )
            } else if (selectedPlace == null){
                BottomCoordButton(
                    onNavigateToResults = { navController.navigate("results") },
                    onAddAction = {
                        showBottomActionButtons = true
                        showMapCreatePointRouteMenu = false
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 300.dp)
                )
            }




            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::updateSearchQuery,
                performSearch = viewModel::performSearch,
                searchResults = searchResults,
                onResultSelected = viewModel::selectSearchResult,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )

            if (showMapLayersMenu) {

                MapLayersMenu(
                    selectedLayer = selectedLayer,
                    selectedOption = selectedOption,
                    onLayerSelected = { layer -> viewModel.updateMapLayer(layer) },
                    onOptionSelected = { option -> viewModel.updateMapOption(option) },
                    onDismiss = { showMapLayersMenu = false }

                )
            }

            if (showMapCreatePointRouteMenu) {
                MapNewRoutePointMenu(
                    selectedRoutePoint = selectedRoutePoint,
                    onLayerSelected = { routePoint ->
                        viewModel.selectRoutePoint(routePoint)
                        if (routePoint == MapNewPointRoute.CREATE_POINT) {
                            println("DEBUG: Seleccionando CREATE_POINT")
                            showMapCreatePointRouteMenu = false
                            showMapNewPointMenu = true
                            isSelectingPoint = true
                            println("DEBUG: isSelectingPoint = $isSelectingPoint")
                        }
                    },
                    onDismiss = {
                        showMapCreatePointRouteMenu = false
                        showBottomActionButtons = true
                    }
                )
            }

            if (showMapNewPointMenu) {
                MapNewPointMenu(
                    selectedRoutePoint = selectedRoutePoint,
                    selectedPoint = viewModel.selectedPoint.value,
                    selectedAddress = viewModel.selectedAddress.value,
                    onLayerSelected = { },
                    onDismiss = {
                        showMapNewPointMenu = false
                        showBottomActionButtons = true
                        isSelectingPoint = false
                        viewModel.clearSelectedPoint()
                    },
                    onClearAndDismiss = {
                        viewModel.clearSelectedPoint()
                        showBottomActionButtons = false
                        isSelectingPoint = true

                    },
                    onConfirm = {
                        showMapNewPointMenu = false
                        showMapNewPointNameMenu = true
                    }
                )
            }

            if (showMapNewPointNameMenu) {
                MapNewPointNameMenu(
                    selectedPoint = viewModel.selectedPoint.value,
                    selectedAddress = viewModel.selectedAddress.value,
                    onDismiss = {
                        showMapNewPointNameMenu = false
                        showBottomActionButtons = true
                        isSelectingPoint = false
                        viewModel.clearSelectedPoint()
                    },
                    onNameConfirmed = { name ->
                        viewModel.saveNewPoint(name)
                        showMapNewPointNameMenu = false
                        showBottomActionButtons = true
                    },
                    onConfirm = {
                        showMapNewPointNameMenu = false
                        showMapNewPointTagMenu = true

                    }
                )
            }

            if(showMapNewPointTagMenu){
                MapNewPointTagMenu(
                    selectedPoint = viewModel.selectedPoint.value,
                    onDismiss = {
                        showMapNewPointTagMenu = false
                        showBottomActionButtons = true
                        isSelectingPoint = false
                        viewModel.clearSelectedPoint()
                    },
                    selectedAddress = viewModel.selectedAddress.value,
                    onNameConfirmed = viewModel.selectedAddress.value,
                    onConfirm = {
                        showMapNewPointTagMenu = false
                        showMapNewPointTagServicesMenu = true

                    }
                )
            }

            if(showMapNewPointTagServicesMenu){
                MapNewPointTagServicesMenu(
                    selectedPoint = viewModel.selectedPoint.value,
                    onDismiss = {
                        showMapNewPointTagServicesMenu = false
                        showBottomActionButtons = true
                        isSelectingPoint = false
                        viewModel.clearSelectedPoint()
                    },
                    selectedAddress = viewModel.selectedAddress.value,
                    onNameConfirmed = viewModel.selectedAddress.value
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(
        navController = navController,
        isPreview = true
    )
}

