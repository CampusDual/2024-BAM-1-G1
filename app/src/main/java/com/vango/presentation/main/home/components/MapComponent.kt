package com.vango.presentation.main.home.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.vango.R
import com.vango.shared.dtos.places.PlacesResponseDto

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
    onPlaceSelected: (PlacesResponseDto?) -> Unit = {}
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

    val markerStates = remember { mutableStateListOf<MarkerState>() }
    var firstLoadMarkers by remember { mutableStateOf(true) }
    var selectedPlace by remember { mutableStateOf<PlacesResponseDto?>(null) }
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

    LaunchedEffect(nearbyPlaces) {
        markerStates.clear()
        nearbyPlaces.forEach { place ->
            place.toLatLng()?.let { latLng ->
                Log.d("MapComponent", "Adding marker for ${place.title} at $latLng")
                markerStates.add(MarkerState(position = latLng))
            } ?: Log.w("MapComponent", "toLatLng() returned null for ${place.title}")
        }
    }

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

            markerStates.forEachIndexed { index, markerState ->
                if (index < nearbyPlaces.size) {
                    val place = nearbyPlaces[index]
                    Marker(
                        state = markerState,
                        title = place.title,
                        snippet = place.address,
                        icon = when (place.type) {
                            0 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_camping)
                            1 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_parking)
                            2 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_hospital)
                            3 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_gas_station)
                            4 -> BitmapDescriptorFactory.fromResource(R.drawable.marker_laundry)
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
                    .height(177.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(start= 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 50.dp)
                ){
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
                Column(){
                    Text(
                        text = "${place.rating ?: 0.0} ★",
                        fontSize = 19.4.sp,
                        color = Color.Black                    )
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