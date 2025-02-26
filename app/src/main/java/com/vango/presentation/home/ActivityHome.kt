package com.vango.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.CameraPosition
import com.google.errorprone.annotations.Modifier
import dagger.hilt.android.AndroidEntryPoint
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.type.LatLng


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
}

@Composable
fun HomeScreen() {
    val defaultLocation = remember {
        com.google.android.gms.maps.model.LatLng(
            40.416775,
            -3.703790
        )
    } // Madrid coordinates
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    )
}