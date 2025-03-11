package com.vango.presentation.main.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorCard
import com.vango.presentation.theme.BackgroundUnselected
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapLayersMenu(
    selectedLayer: MapLayer,
    selectedOption: MapOption?,
    onLayerSelected: (MapLayer) -> Unit,
    onOptionSelected: (MapOption?) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        scrimColor = Color.Black.copy(alpha = 0.4f),
        windowInsets = WindowInsets(0.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ex),
                    contentDescription = "Cerrar",
                    modifier = Modifier
                        .size(17.dp)
                        .clickable {
                            scope.launch { sheetState.hide() }
                            onDismiss()
                        },
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Configuración del mapa",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .background(BackgroundColorCard, shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .height(130.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "Tipo de mapa",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MapLayerButton(
                            text = "Predefinido",
                            iconRes = R.drawable.preset,
                            isSelected = selectedLayer == MapLayer.NORMAL,
                            onClick = { onLayerSelected(MapLayer.NORMAL) }
                        )
                        MapLayerButton(
                            text = "Satélite",
                            iconRes = R.drawable.satellite,
                            isSelected = selectedLayer == MapLayer.SATELLITE,
                            onClick = { onLayerSelected(MapLayer.SATELLITE) }
                        )
                        MapLayerButton(
                            text = "Terreno",
                            iconRes = R.drawable.terrain,
                            isSelected = selectedLayer == MapLayer.RELIEF,
                            onClick = { onLayerSelected(MapLayer.RELIEF) }
                        )
                        MapLayerButton(
                            text = "Sin conexión",
                            iconRes = R.drawable.save_map,
                            isSelected = selectedLayer == MapLayer.NO_CONNECTION,
                            onClick = { onLayerSelected(MapLayer.NO_CONNECTION) }
                        )
                    }

                }

            }

            Box(
                modifier = Modifier
                    .background(BackgroundColorCard, shape = RoundedCornerShape(20.dp))
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .height(130.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "Opciones del mapa",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MapOptionButton(
                            text = "Tráfico",
                            iconRes = R.drawable.road,
                            iconResFull = R.drawable.road_full,
                            isSelected = selectedOption == MapOption.TRAFFIC,
                            onClick = {
                                onOptionSelected(if (selectedOption == MapOption.TRAFFIC) null else MapOption.TRAFFIC)
                            }
                        )
                        MapOptionButton(
                            text = "Tiempo",
                            iconRes = R.drawable.weather,
                            iconResFull = R.drawable.weather_full,
                            isSelected = selectedOption == MapOption.WEATHER,
                            onClick = {
                                onOptionSelected(if (selectedOption == MapOption.WEATHER) null else MapOption.WEATHER)
                            }
                        )
                        MapOptionButton(
                            text = "Transporte\nPúblico",
                            iconRes = R.drawable.traffic,
                            iconResFull = R.drawable.traffic_full,
                            isSelected = selectedOption == MapOption.PUBLIC_TRANSPORT,
                            onClick = {
                                onOptionSelected(if (selectedOption == MapOption.PUBLIC_TRANSPORT) null else MapOption.PUBLIC_TRANSPORT)
                            }
                        )
                        Spacer(modifier = Modifier.width(60.dp))
                    }
                }

            }
        }
    }
}

@Composable
fun MapLayerButton(
    text: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            onClick = onClick,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.Unspecified,

            border = if (isSelected) BorderStroke(2.dp, BackgroundButtonColor) else null
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp),

                )


        }
        Text(
            text = text,
            modifier = Modifier.padding(top = 6.dp),
            fontSize = 10.sp,
            color = Color.Black,
            maxLines = 3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MapOptionButton(
    text: String,
    iconRes: Int,
    iconResFull: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            onClick = onClick,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            shape = RoundedCornerShape(16.dp),
            color = if(isSelected) BackgroundButtonColor else BackgroundUnselected,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = if(isSelected) painterResource(id = iconResFull) else painterResource(id = iconRes),
                    contentDescription = text,
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
            }
        }
        Text(
            text = text,
            modifier = Modifier.padding(top = 6.dp),
            fontSize = 10.sp,
            color = Color.Black,
            maxLines = 3,
            textAlign = TextAlign.Center
        )
    }
}


enum class MapLayer {
    NORMAL,
    RELIEF,
    SATELLITE,
    NO_CONNECTION
}

enum class MapOption {
    TRAFFIC,
    WEATHER,
    PUBLIC_TRANSPORT
}

@Preview(showBackground = true)
@Composable
fun MapLayersBottomSheetPreview() {
    MapLayersMenu(
        onLayerSelected = {},
        onOptionSelected = {},
        selectedLayer = MapLayer.NORMAL,
        selectedOption = MapOption.TRAFFIC,
        onDismiss = {}
    )
}