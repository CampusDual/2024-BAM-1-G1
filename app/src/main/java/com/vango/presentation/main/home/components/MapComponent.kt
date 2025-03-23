package com.vango.presentation.main.home.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.vango.R
import com.vango.presentation.theme.BackgroundColorButtonPrincipal
import com.vango.presentation.theme.BackgroundUnselected
import com.vango.presentation.theme.BlackGray
import com.vango.presentation.theme.MainColor
import com.vango.shared.dtos.places.PlacesResponseDto
import kotlinx.coroutines.launch

@Composable
fun BitmapDescriptorFactory.fromResource(drawableId: Int, context: Context): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    val bitmap = Bitmap.createBitmap(
        drawable?.intrinsicWidth ?: 33,
        drawable?.intrinsicHeight ?: 40,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

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
    isShowingRoutePoint: Boolean = false,
    onMapLoadedCallback: () -> Unit = {},
    nearbyPlaces: List<PlacesResponseDto>,
    selectedFilterTypes: Set<Int>,
    onPlaceSelected: (PlacesResponseDto?) -> Unit = {},
    onFullScreenChanged: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val mapStyleOptions = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    }

    val mapProperties = remember(selectedLayer) {
        MapProperties(
            mapType = when (selectedLayer) {
                MapLayer.NORMAL -> MapType.NORMAL
                MapLayer.SATELLITE -> MapType.SATELLITE
                MapLayer.RELIEF -> MapType.TERRAIN
                MapLayer.NO_CONNECTION -> MapType.NONE
            },
            isMyLocationEnabled = isLocationEnabled,
            mapStyleOptions = mapStyleOptions
        )
    }

    val mapUiSettings = remember {
        MapUiSettings(
            compassEnabled = true,
            zoomControlsEnabled = false
        )
    }

//    var firstLoadMarkers by remember { mutableStateOf(true) }
    val markerStatesMap = remember { mutableStateMapOf<String, MarkerState>() }
    var selectedPlace by remember { mutableStateOf<PlacesResponseDto?>(null) }
    var showFullScreen by remember { mutableStateOf(false) }

    val filteredPlaces = remember(nearbyPlaces, selectedFilterTypes) {
        val filtered = if (selectedFilterTypes.isEmpty()) {
            nearbyPlaces
        } else {
            nearbyPlaces.filter { place ->
                place.type in selectedFilterTypes
            }
        }
        Log.d(
            "MapComponent",
            "NearbyPlaces: ${nearbyPlaces.size}, FilteredPlaces: ${filtered.size}, Filters: $selectedFilterTypes"
        )
        filtered.forEach { place ->
            Log.d(
                "MapComponent",
                "Filtered Place: ${place.title}, Type: ${place.type}, PlaceId: ${place.placeId}"
            )
        }
        filtered
    }

//    LaunchedEffect(nearbyPlaces) {
//        if (firstLoadMarkers && nearbyPlaces.isNotEmpty()) {
//            markerStates.clear()
//            nearbyPlaces.forEach { place ->
//                place.toLatLng()?.let { latLng ->
//                    Log.d("MapComponent", "Adding marker for ${place.title} at $latLng")
//                    markerStates.add(MarkerState(position = latLng))
//                } ?: Log.w("MapComponent", "toLatLng() returned null for ${place.title}")
//            }
//            firstLoadMarkers = false
//        }
//    }

    LaunchedEffect(filteredPlaces) {
        val currentPlaceIds = filteredPlaces.map { it.placeId }.toSet()
        markerStatesMap.keys.retainAll(currentPlaceIds)

        filteredPlaces.forEach { place ->
            place.placeId?.let { placeId ->
                if (!markerStatesMap.containsKey(placeId)) {
                    place.toLatLng()?.let { latLng ->
                        Log.d("MapComponent", "Adding marker for ${place.title} at $latLng")
                        markerStatesMap[placeId] = MarkerState(position = latLng)
                    } ?: Log.w("MapComponent", "toLatLng() returned null for ${place.title}")
                }
            }
        }
    }

    LaunchedEffect(showFullScreen) {
        onFullScreenChanged(showFullScreen) // Notifica al HomeScreen
    }

//    LaunchedEffect(nearbyPlaces) {
//        markerStates.clear()
//        nearbyPlaces.forEach { place ->
//            place.toLatLng()?.let { latLng ->
//                Log.d("MapComponent", "Adding marker for ${place.title} at $latLng")
//                markerStates.add(MarkerState(position = latLng))
//            } ?: Log.w("MapComponent", "toLatLng() returned null for ${place.title}")
//        }
//    }

    Box(modifier = modifier.fillMaxSize()) {
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
                onMapLoadedCallback()
            },
            onMapClick = {
                onMapClick(it)
                selectedPlace = null
                onPlaceSelected(null)
            }
        ) {
            if (isLocationEnabled && currentLocation.latitude != 0.0 && currentLocation.longitude != 0.0) {

                if (!isSelectingPoint && !isShowingRoutePoint) {
                    Marker(
                        state = MarkerState(position = currentLocation),
                        title = "Ubicación actual"
                    )
                }
            }

            filteredPlaces.forEach { place ->
                place.placeId?.let { placeId ->
                    val markerState = remember(placeId) {
                        place.toLatLng()?.let { MarkerState(position = it) }
                            ?: run {
                                Log.w("MapComponent", "toLatLng() returned null for ${place.title}")
                                null
                            }
                    }
                    markerState?.let {
                        Marker(
                            state = it,
                            title = place.title,
                            snippet = place.address,
                            icon = when (place.type) {
                                0 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_camping2)
                                1 -> BitmapDescriptorFactory.fromResource(R.drawable.parking_test)
                                2 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital2)
                                3 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_gas_station2)
                                4 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_laundry2)
                                else -> null
                            },
                            onClick = {
                                selectedPlace = place
                                onPlaceSelected(place)
                                true
                            }
                        )
                    }
                }
            }


//            markerStates.forEachIndexed { index, markerState ->
//                val place = nearbyPlaces[index]
//                Marker(
//                    state = markerState,
//                    title = place.title,
//                    snippet = place.address,
//                    icon = when (place.type) {
//
//                        0 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_camping)
//                        1 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_parking)
//                        2 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital)
//                        3 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_gas_station)
//                        4 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_laundry)
//                        else -> null
//                    },
//                    onClick = {
//                        selectedPlace = place
//                        onPlaceSelected(place)
//                        true
//                    }
//                )
//            }

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

        selectedPlace?.let { place ->
            PlaceCard(
                place = place,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
                    .fillMaxWidth()
                    .clickable {
                        showFullScreen = true
                    }
            )
        }
    }

    if (showFullScreen && selectedPlace != null) {
        FullScreenPlaceCard(
            place = selectedPlace!!,
            onDismiss = { showFullScreen = false }
        )
    }
    LaunchedEffect(cameraPositionState.position) {
        val bounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
        bounds?.let {
            onLocationVisibilityChanged(it.contains(currentLocation))
        }
    }
}

enum class PlaceType(val value: Int) {
    CAMPING(0),
    PARKING(1),
    HOSPITAL(2),
    GASOLINERA(3),
    LAVANDERIA(4);

    companion object {
        fun fromValue(value: Int): String =
            values().find { it.value == value }?.name?.lowercase() ?: "desconocido"
    }
}

data class Place(val type: Int)

@Composable
fun PlaceCard(
    place: PlacesResponseDto,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(329.dp)
            .heightIn()
            .padding(start = 20.dp, end = 20.dp),

        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                        .height(17.5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Surface(
                        modifier = Modifier
                            .height(17.5.dp)
                            .wrapContentWidth(),
                        color = Color.White.copy(alpha = 0.54f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "#${place.type?.let { PlaceType.fromValue(it) } ?: "desconocido"}",
                                fontSize = 8.sp,
                                textAlign = TextAlign.Center,
                                color = BackgroundUnselected,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                        }

                    }

                    Icon(
                        painter = painterResource(id = R.drawable.heart),
                        tint = Color.Black,
                        contentDescription = "favorite"

                    )

                }

                place.photoUrls?.firstOrNull()?.let { photoUrl ->
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = "Imagen de ${place.title}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(177.dp),
                        contentScale = ContentScale.Crop
                    )
                } ?: AsyncImage(
                    model = R.drawable.noimage,
                    contentDescription = "Imagen por defecto para ${place.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(229.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 50.dp)
                ) {
                    Text(
                        text = place.title ?: "Sin título",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = place.address ?: "Sin dirección",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Column() {
                    Text(
                        text = "${place.rating ?: 0.0} ★",
                        fontSize = 19.4.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${place.userVotes ?: 0} votos",
                        fontSize = 9.7.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}

@Composable
fun FullScreenPlaceCard(
    place: PlacesResponseDto,
    onDismiss: () -> Unit
) {
    val offsetY = remember { Animatable(600f) }
    val scope = rememberCoroutineScope()
    var showFullMap by remember { mutableStateOf(false) }
    val miniMapCameraPositionState = rememberCameraPositionState {
        place.toLatLng()?.let { latLng ->
            position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    }

    val fullMapCameraPositionState = rememberCameraPositionState {
        place.toLatLng()?.let { latLng ->
            position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    }
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.BottomCenter)
                .offset(y = offsetY.value.dp),
//            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 20.dp, end = 20.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Surface(
//                        color = MainColor,
//                        modifier = Modifier.size(32.dp),
//                        shape = RoundedCornerShape(11.dp),
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center,
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ex),
//                                contentDescription = "Cerrar",
//                                modifier = Modifier
//                                    .width(12.5.dp)
//                                    .height(14.29.dp)
//                                    .clickable {
//                                        scope.launch {
//                                            offsetY.animateTo(600f, animationSpec = tween(300))
//                                            onDismiss()
//                                        }
//                                    },
//                                tint = Color.White
//                            )
//
//                        }
//                    }
//                    Icon(
//                        painter = painterResource(id = R.drawable.ex),
//                        contentDescription = "Cerrar",
//                        modifier = Modifier
//                            .size(32.dp)
//                            .clickable {
//                                scope.launch {
//                                    offsetY.animateTo(600f, animationSpec = tween(300))
//                                    onDismiss()
//                                }
//                            }
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                place.photoUrls?.firstOrNull()?.let { photoUrl ->
//                    AsyncImage(
//                        model = photoUrl,
//                        contentDescription = "Imagen de ${place.title}",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(300.dp),
//                        contentScale = ContentScale.Crop
//                    )
//                } ?: AsyncImage(
//                    model = R.drawable.noimage,
//                    contentDescription = "Imagen por defecto",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(300.dp),
//                    contentScale = ContentScale.Crop
//                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    place.photoUrls?.firstOrNull()?.let { photoUrl ->
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = "Imagen de ${place.title}",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } ?: AsyncImage(
                        model = R.drawable.noimage,
                        contentDescription = "Imagen por defecto",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, top = 55.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = MainColor,
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(11.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ex),
                                    contentDescription = "Cerrar",
                                    modifier = Modifier
                                        .width(12.5.dp)
                                        .height(14.29.dp)
                                        .clickable {
                                            scope.launch {
                                                offsetY.animateTo(600f, animationSpec = tween(300))
                                                onDismiss()
                                            }
                                        },
                                    tint = Color.White
                                )
                            }
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ex),
                            contentDescription = "Cerrar",
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    scope.launch {
                                        offsetY.animateTo(600f, animationSpec = tween(300))
                                        onDismiss()
                                    }
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(41.86.dp),
                        border = BorderStroke(0.5.dp, BlackGray),
                        shape = RoundedCornerShape(15.dp),
                        color = Color.White
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ex),
                                tint = Color.Black,
                                modifier = Modifier
                                    .width(12.5.dp)
                                    .height(14.29.dp)
                                    .clickable {
                                        scope.launch {
                                            offsetY.animateTo(600f, animationSpec = tween(300))
                                            onDismiss()
                                        }
                                    },

                                contentDescription = "favorite"
                            )
                        }


                    }

                    Surface(
                        modifier = Modifier.size(41.86.dp),
                        border = BorderStroke(0.5.dp, BlackGray),
                        shape = RoundedCornerShape(15.dp),
                        color = Color.White
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ex),
                                tint = Color.Black,
                                modifier = Modifier
                                    .width(12.5.dp)
                                    .height(14.29.dp)
                                    .clickable {
                                        scope.launch {
                                            offsetY.animateTo(600f, animationSpec = tween(300))
                                            onDismiss()
                                        }
                                    },

                                contentDescription = "favorite"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 50.dp)
                    ) {
                        Text(
                            text = place.title ?: "Sin título",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = place.address ?: "Sin dirección",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "${place.rating ?: 0.0} ★",
                            fontSize = 21.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${place.userVotes ?: 0} votos",
                            fontSize = 9.7.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))




                place.toLatLng()?.let { latLng ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(195.dp)
                    ) {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = miniMapCameraPositionState,
                            properties = MapProperties(
                                isMyLocationEnabled = false,
                                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                                    LocalContext.current,
                                    R.raw.map_style
                                )
                            ),
                            uiSettings = MapUiSettings(
                                zoomControlsEnabled = false,
                                compassEnabled = false,
                                myLocationButtonEnabled = false,
                                scrollGesturesEnabled = false,
                                zoomGesturesEnabled = false,
                                tiltGesturesEnabled = false,
//                                rotateGesturesEnabled = false
                            )
                        ) {
                            Marker(
                                state = MarkerState(position = latLng),
                                title = place.title,
                                icon = when (place.type) {
                                    0 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_camping2)
                                    1 -> BitmapDescriptorFactory.fromResource(R.drawable.parking_test)
                                    2 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital2)
                                    3 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_gas_station2)
                                    4 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_laundry2)
                                    else -> null
                                }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { showFullMap = true }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.location_no_fill),
                                tint = Color.Black,
                                modifier = Modifier
                                    .width(18.dp)
                                    .height(18.dp)
                                    .clickable {
                                        scope.launch {
                                            offsetY.animateTo(600f, animationSpec = tween(300))
                                            onDismiss()
                                        }
                                    },

                                contentDescription = "favorite"
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            place.address?.let {
                                Text(
                                    text = it,
                                    overflow = TextOverflow.Ellipsis

                                )
                            }
                        }

                        HorizontalDivider(color = Color.LightGray)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.clock),
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp)
                                        .clickable {
                                            scope.launch {
                                                offsetY.animateTo(600f, animationSpec = tween(300))
                                                onDismiss()
                                            }
                                        },
                                    contentDescription = "favorite"
                                )
                                Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = "Lunes a Domingo\n" +
                                                "9am a 9pm",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        overflow = TextOverflow.Ellipsis

                                    )

                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.calendar),
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp),
                                    contentDescription = "favorite"
                                )
                                Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = "Abierto todo el año",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        overflow = TextOverflow.Ellipsis

                                    )

                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        HorizontalDivider(color = Color.LightGray)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.phone),
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp)
                                        .clickable {
                                            scope.launch {
                                                offsetY.animateTo(600f, animationSpec = tween(300))
                                                onDismiss()
                                            }
                                        },
                                    contentDescription = "favorite"
                                )
                                Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = "+34 658 587 254",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        overflow = TextOverflow.Ellipsis

                                    )

                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arroba),
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp)
                                        .clickable {
                                            scope.launch {
                                                offsetY.animateTo(600f, animationSpec = tween(300))
                                                onDismiss()
                                            }
                                        },
                                    contentDescription = "favorite"
                                )
                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    text = "info@jabaliblanco.es",
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    overflow = TextOverflow.Ellipsis

                                )

                            }

                            Spacer(modifier = Modifier.weight(1f))
                        }

                        HorizontalDivider(color = Color.LightGray)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.www),
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .width(18.dp)
                                        .height(18.dp)
                                        .clickable {
                                            scope.launch {
                                                offsetY.animateTo(600f, animationSpec = tween(300))
                                                onDismiss()
                                            }
                                        },
                                    contentDescription = "favorite"
                                )
                                Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = "www.jabaliblanco.es",
                                        fontSize = 12.sp,
                                        color = Color.Black,
                                        overflow = TextOverflow.Ellipsis

                                    )

                            }


                        }




                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 150.dp),
                    horizontalArrangement = Arrangement.Center
                ){
                    Surface(
                        modifier = Modifier
                            .width(156.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = MainColor
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Navegador",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis

                                )
                        }

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Surface(
                        modifier = Modifier
                            .width(156.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = BackgroundColorButtonPrincipal
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                text = "Reservar",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis


                            )
                        }

                    }
                }




            }

        }

        if (showFullMap) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { showFullMap = false }
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                        .align(Alignment.Center)
                        .background(Color.White)
                        .clip(RoundedCornerShape(15.dp)),
                    cameraPositionState = fullMapCameraPositionState,
                    properties = MapProperties(
                        isMyLocationEnabled = false,
                        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                            LocalContext.current,
                            R.raw.map_style
                        )
                    ),
                    uiSettings = MapUiSettings(
                        zoomControlsEnabled = true,
                        compassEnabled = true,
                        myLocationButtonEnabled = false
                    )
                ) {
                    Marker(
                        state = MarkerState(position = place.toLatLng()!!),
                        title = place.title,
                        icon = when (place.type) {
                            0 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_camping2)
                            1 -> BitmapDescriptorFactory.fromResource(R.drawable.parking_test)
                            2 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital2)
                            3 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_gas_station2)
                            4 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_laundry2)
                            else -> null
                        }
                    )
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 55.dp, start = 20.dp)
                        .size(32.dp),
                    shape = RoundedCornerShape(11.dp),
                    color = MainColor,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Volver atrás",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { showFullMap = false },
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceCardPreview() {
    val samplePlace = PlacesResponseDto(
        title = "Parking de La Tella Parking de La Tella Parking de La Tella",
        address = "HU-631",
        location = "42.5624343,0.0425323",
        rating = 4.3f,
        userVotes = 108,
        photoUrls = listOf("https://gratisography.com/wp-content/uploads/2025/02/gratisography-when-pigs-fly-1170x780.jpg"), // Imagen real
        type = 1,
        placeId = "ChIJc-IaFVwBqBIRl6QdE2o8gu8",
        currentOpeningHours = null,
        opening_hours = null,
        international_phone_number = null,
        formattedPhoneNumber = null,
        website = null,
        reviews = null,
        source = 1
    )

    PlaceCard(
        place = samplePlace,
        modifier = Modifier
            .fillMaxWidth()
    )
}


@Preview(showBackground = true, heightDp = 1200) // Aumentamos la altura de la vista previa
@Composable
fun PlaceCardFullScreenPreview() {
    val samplePlace = PlacesResponseDto(
        title = "Parking de La Tella Parking de La Tella Parking de La Tella",
        address = "HU-631", // Dirección más larga para simular más contenido
        location = "42.5624343,0.0425323",
        rating = 4.3f,
        userVotes = 108,
        photoUrls = listOf("https://gratisography.com/wp-content/uploads/2025/02/gratisography-when-pigs-fly-1170x780.jpg"),
        type = 1,
        placeId = "ChIJc-IaFVwBqBIRl6QdE2o8gu8",
        currentOpeningHours = null,
        opening_hours = null,
        international_phone_number = null,
        formattedPhoneNumber = null,
        website = null,
        reviews = null,
        source = 1
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1200.dp)
            .background(Color.Gray)
    ) {
        FullScreenPlaceCard(
            place = samplePlace,
            onDismiss = {}
        )
    }
}