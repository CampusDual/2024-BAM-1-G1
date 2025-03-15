package com.vango.presentation.main.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorCard
import com.vango.presentation.theme.MainColor
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapNewRoutePointMenu(
    selectedRoutePoint: MapNewPointRoute?,
    onLayerSelected: (MapNewPointRoute) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    val density = LocalDensity.current
    val navbarHeight = with(density) { 48.dp.toPx() }

    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .align(Alignment.BottomCenter)
                .offset(y = offsetY.value.dp),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
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
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "¿Qué quieres crear?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .background(BackgroundColorCard, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(149.dp),
                    contentAlignment = Alignment.Center

                    ) {

                        Column(
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            MapNewRoutePointButton(
                                text = "Crear ruta",
                                iconRes = R.drawable.ruta,
                                tint = Color.White,
                                color = MainColor,
                                onClick = {
                                    onLayerSelected(MapNewPointRoute.CREATE_ROUTE)
                                }
                            )

                            Spacer(
                                modifier = Modifier.height(13.dp),
                            )

                            MapNewRoutePointButton(
                                text = "Crear punto",
                                iconRes = R.drawable.marker,
                                tint = Color.White,
                                color = BackgroundButtonColor,
                                onClick = {
                                    onLayerSelected(MapNewPointRoute.CREATE_ROUTE)
                                }
                            )
                        }

                }
            }
        }
    }
}

@Composable
fun MapNewRoutePointButton(
    text: String,
    iconRes: Int,
    tint: Color,
    color : Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            onClick = onClick,
            modifier = Modifier
                .width(252.dp)
                .height(51.dp),

            shape = RoundedCornerShape(20.dp),
            color = color,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    modifier = Modifier.size(36.dp),
                    tint = tint
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = text,
                    modifier = Modifier.padding(top = 6.dp),
                    fontSize = 12.sp,
                    color = Color.White,
                    maxLines = 3,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

enum class MapNewPointRoute {
    CREATE_ROUTE,
    CREATE_POINT

}

@Preview(showBackground = true)
@Composable
fun MapNewRoutePointMenuPreview() {
    MapNewRoutePointMenu(
        onLayerSelected = {},
        selectedRoutePoint = MapNewPointRoute.CREATE_ROUTE,
        onDismiss = {}
    )
}