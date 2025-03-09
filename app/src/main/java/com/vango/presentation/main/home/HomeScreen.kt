package com.vango.presentation.main.home

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorBadge
import com.vango.presentation.theme.BackgroundColorList

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
    
    var searchQuery by remember { mutableStateOf("") }

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

    fun performSearch(query: String) {
        if (query.lowercase() == "madrid") {
            val madridLocation = LatLng(40.416775, -3.703790)
            cameraState.position = CameraPosition.fromLatLngZoom(madridLocation, 12f)
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


            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 20.dp, top = 120.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {


                        Surface(
                            onClick = { moveYourLocation() },
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
                            shape = RoundedCornerShape(13.dp),
                            color = BackgroundButtonColor
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.location),
                                    contentDescription = "Ubicación",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.White
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
                            shape = RoundedCornerShape(13.dp),
                            color = BackgroundButtonColor
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.map_type_fill),
                                    contentDescription = "tipo de mapa",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.White
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
                            shape = RoundedCornerShape(13.dp),
                            color = BackgroundButtonColor
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.heart),
                                    contentDescription = "tipo de mapa",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.White
                                )
                            }
                        }
                        Surface(
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
                            shape = RoundedCornerShape(13.dp),
                            color = BackgroundButtonColor
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.road),
                                    contentDescription = "tipo de mapa",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }

            }

            TextField(
                value = searchQuery,
                shape = RoundedCornerShape(100.dp),
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .shadow(4.dp, RoundedCornerShape(8.dp)),
                placeholder = { Text("Empieza a buscar") },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Buscar",
                        modifier = Modifier
                            .clickable {
                                performSearch(searchQuery)
                            }
                            .size(24.dp),
                        tint = Color.Gray
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { performSearch(searchQuery) }
                )
            )


            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Surface(
                            onClick = { navController.navigate("results") },
                            modifier = Modifier
                                .width(98.dp)
                                .height(51.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
                            shape = RoundedCornerShape(13.dp),
                            color = BackgroundColorList
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.list),
                                    contentDescription = "Ubicación",
                                    modifier = Modifier.size(25.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Lista",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

            }
            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_btn),
                        contentDescription = "Añadir Punto Nuevo",
                        modifier = Modifier
                            .size(height = 53.dp, width = 45.dp)
                            .clickable { navController.navigate("results") }
                            .align(Alignment.BottomCenter)
                    )

                }

            }


            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 120.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Surface(
                            onClick = { navController.navigate("results") },
                            modifier = Modifier
                                .width(127.dp)
                                .height(40.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(13.dp)),
                            shape = RoundedCornerShape(13.dp),
                            color = BackgroundColorList
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Buscar aquí",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

            }

            FilterButton(
                onMoveYourLocation = { moveYourLocation() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 120.dp, start = 20.dp)
            )


        }
    }
}

@Composable
fun FilterButton(
    onMoveYourLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isCampingSelected by remember { mutableStateOf(false) }
    var isParkingSelected by remember { mutableStateOf(false) }
    var isHospitalSelected by remember { mutableStateOf(false) }
    var isFuelStationSelected by remember { mutableStateOf(false) }
    var isLaundrySelected by remember { mutableStateOf(false) }

    val selectedCount = listOf(
        isCampingSelected,
        isParkingSelected,
        isHospitalSelected,
        isFuelStationSelected,
        isLaundrySelected
    ).count { it }

    Box(

        modifier = Modifier
            .clickable(
                enabled = isExpanded,
                onClick = { isExpanded = false },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ).then(if (isExpanded) Modifier.fillMaxSize() else Modifier)
    ) {

        Box {
            Surface(
                onClick = { isExpanded = !isExpanded },
                modifier = modifier
                    .width(60.dp)
                    .height(60.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = BackgroundButtonColor
            ) {
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = if (isExpanded || selectedCount > 0) painterResource(id = R.drawable.filter_fill) else painterResource(
                            id = R.drawable.filter_no_fill
                        ),
                        contentDescription = "Filtros",
                        modifier = Modifier.size(25.dp),
                        tint = Color.White
                    )
                    Text(
                        text = "Filtros",
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }

            if (selectedCount > 0) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 110.dp, start = 67.dp)
                        .size(22.dp)
                        .shadow(elevation = 2.dp, shape = CircleShape),
                    shape = CircleShape,
                    color = BackgroundColorBadge
                ) {
                    Text(
                        text = selectedCount.toString(),
                        color = Color.White,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 188.dp, start = 20.dp)
                    .width(60.dp)
                    .background(Color.Transparent)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 0)
                        ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 0))
                    ) {
                        FilterOption(
                            text = "Camping",
                            painter = if (isCampingSelected) painterResource(id = R.drawable.camper_fill) else painterResource(
                                id = R.drawable.camper_no_fill
                            ),
                            isSelected = isCampingSelected,
                            onClick = { isCampingSelected = !isCampingSelected }
                        )
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 100)
                        ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 100))
                    ) {
                        FilterOption(
                            text = "Parking",
                            painter = if (isParkingSelected) painterResource(id = R.drawable.parking_fill) else painterResource(
                                id = R.drawable.parking_no_fill
                            ),
                            isSelected = isParkingSelected,
                            onClick = { isParkingSelected = !isParkingSelected }
                        )
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 200)
                        ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 200))
                    ) {
                        FilterOption(
                            text = "Hospital",
                            painter = if (isHospitalSelected) painterResource(id = R.drawable.hospital_fill) else painterResource(
                                id = R.drawable.hospital_no_fill
                            ),
                            isSelected = isHospitalSelected,
                            onClick = { isHospitalSelected = !isHospitalSelected }
                        )
                    }

                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 200)
                        ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 200))
                    ) {
                        FilterOption(
                            text = "Gasolinera",
                            painter = if (isFuelStationSelected) painterResource(id = R.drawable.fuel_station_fill) else painterResource(
                                id = R.drawable.fuel_station_no_fill
                            ),
                            isSelected = isFuelStationSelected,
                            onClick = { isFuelStationSelected = !isFuelStationSelected }
                        )
                    }
                    AnimatedVisibility(
                        visible = isExpanded,
                        enter = slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(durationMillis = 300, delayMillis = 200)
                        ) + fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 200))
                    ) {
                        FilterOption(
                            text = "Lavanderia",
                            painter = if (isLaundrySelected) painterResource(id = R.drawable.laundry_fill) else painterResource(
                                id = R.drawable.laundry_no_fill
                            ),
                            isSelected = isLaundrySelected,
                            onClick = { isLaundrySelected = !isLaundrySelected }
                        )
                    }
                }
            }
        }

    }


}

@Composable
fun FilterOption(
    text: String,
    painter: androidx.compose.ui.graphics.painter.Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) BackgroundButtonColor else Color.Gray
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = text,
                modifier = Modifier.size(25.dp),
                tint = Color.White
            )

            Text(
                text = text,
                modifier = Modifier.padding(top = 3.dp),
                fontSize = 10.sp,
                color = Color.White
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