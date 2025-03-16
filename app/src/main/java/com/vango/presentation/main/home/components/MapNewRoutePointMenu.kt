package com.vango.presentation.main.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.LatLng
import com.vango.R
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorCard
import com.vango.presentation.theme.BackgroundColorImage
import com.vango.presentation.theme.BackgroundColorList
import com.vango.presentation.theme.BlackGray
import com.vango.presentation.theme.MainColor
import com.vango.presentation.theme.WhiteGray
import kotlinx.coroutines.launch

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
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth(),
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
                                onLayerSelected(MapNewPointRoute.CREATE_POINT)
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun MapNewPointMenu(
    selectedRoutePoint: MapNewPointRoute?,
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onLayerSelected: (MapNewPointRoute) -> Unit,
    onDismiss: () -> Unit,
    onClearAndDismiss: () -> Unit
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
                        text = "Localiza el nuevo punto",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                if (selectedPoint != null && selectedAddress != null) {
                    Box(
                        modifier = Modifier
                            .background(BackgroundColorCard, shape = RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                            .height(113.dp),
                        contentAlignment = Alignment.Center

                    ) {

                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = selectedAddress,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                            Spacer(
                                modifier = Modifier.height(3.dp),
                            )
                            Text(
                                text = "(${selectedPoint.latitude}, ${selectedPoint.longitude})",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(18.dp))


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,

                                ) {

                                Surface(
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp),

                                    shape = RoundedCornerShape(10.dp),
                                    color = Color.Transparent,
                                    border = BorderStroke(0.5.dp, BlackGray)
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
                                                        onClearAndDismiss()
                                                    }
                                                },
                                            tint = Color.Black
                                        )

                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Surface(
                                    modifier = Modifier
                                        .width(32.dp)
                                        .height(32.dp),

                                    shape = RoundedCornerShape(10.dp),
                                    color = MainColor,
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.check),
                                            contentDescription = "Cerrar",
                                            modifier = Modifier
                                                .width(12.5.dp)
                                                .height(14.29.dp)
                                                .clickable {
                                                    scope.launch {
                                                        offsetY.animateTo(
                                                            600f,
                                                            animationSpec = tween(300)
                                                        )
                                                    }
                                                },
                                            tint = Color.White
                                        )

                                    }
                                }

                            }
                        }


                    }


                }


            }
        }
    }
}


@Composable
fun MapNewPointNameMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }
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
                        text = "Añade un nombre \nal nuevo punto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .background(BackgroundColorCard, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(130.dp),
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            ),
                            placeholder = {
                                Text(
                                    "Pon un nombre a este punto",
                                    color = Color.LightGray,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFFFFFFF),
                                unfocusedContainerColor = Color(0xFFFFFFFF),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            Surface(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp),

                                shape = RoundedCornerShape(10.dp),
                                color = Color.Transparent,
                                border = BorderStroke(0.5.dp, BlackGray)
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
//                                                    onClearAndDismiss()
                                                }
                                            },
                                        tint = Color.Black
                                    )

                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Surface(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp),

                                shape = RoundedCornerShape(10.dp),
                                color = MainColor,
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.check),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(12.5.dp)
                                            .height(14.29.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                }
                                            },
                                        tint = Color.White
                                    )

                                }
                            }

                        }
                    }


                }


            }
        }
    }
}


@Composable
fun MapNewPointTagMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .align(Alignment.TopStart)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
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

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Etiqueta este punto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(),
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn()
                                .background(color = WhiteGray, shape = RoundedCornerShape(12.dp)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            Spacer(modifier = Modifier.height(18.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.Start,

                                ) {
                                Text(
                                    text = "Categoría principal",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,


                                ) {

                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )
                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )
                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )
                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )

                            }
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,


                                ) {

                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )

                            }

                            Spacer(modifier = Modifier.height(12.dp))

                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn()
                                .background(color = WhiteGray, shape = RoundedCornerShape(12.dp)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            Spacer(modifier = Modifier.height(18.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.Start,

                                ) {
                                Text(
                                    text = "Subcategorías",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {

                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )
                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )
                                ButtonCategory(
                                    text = "Categoría",
                                    iconRes = R.drawable.ex,
                                    tint = Color.White,
                                    color = MainColor,
                                    onClick = { /*TODO*/ },

                                    )
                                Spacer(modifier = Modifier.width(64.dp))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                    }


                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(51.dp),

                        shape = RoundedCornerShape(20.dp),
                        color = MainColor,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {


                            Text(
                                text = "Siguiente",
                                modifier = Modifier.padding(top = 6.dp),
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                maxLines = 3,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }


            }
        }
    }
}

@Composable
fun MapNewPointConfirmMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .heightIn()
    ) {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            color = BackgroundColorList,
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

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "¿Quieres crear este punto?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(),
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp),
                            horizontalArrangement = Arrangement.Start,

                            ) {
                            Text(
                                text = "Nombre del punto",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MainColor
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Column(
                            modifier = Modifier
                                .padding(start = 20.dp, end = 20.dp)
                                .fillMaxWidth()
                                .height(37.dp)
                                .background(color = WhiteGray, shape = RoundedCornerShape(12.dp)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center

                        ) {
                            Text(
                                text = "address",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp),
                            horizontalArrangement = Arrangement.Start,

                            ) {
                            Text(
                                text = "Dirección del punto",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MainColor
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            Column(
                                modifier = Modifier
                                    .padding(start = 20.dp, end = 20.dp)
                                    .fillMaxWidth()
                                    .height(53.5.dp)
                                    .background(
                                        color = WhiteGray,
                                        shape = RoundedCornerShape(12.dp)
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center

                            ) {

                                Text(
                                    text = "address",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                                Spacer(
                                    modifier = Modifier.height(3.dp),
                                )
                                Text(
                                    text = "coord",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                            }


                        }
                    }


                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(51.dp),

                        shape = RoundedCornerShape(20.dp),
                        color = MainColor,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {


                            Text(
                                text = "Crear punto",
                                modifier = Modifier.padding(top = 6.dp),
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                maxLines = 3,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }


            }
        }
    }
}


@Composable
fun MapNewPointTagServicesMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .align(Alignment.TopStart)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            color = BackgroundColorList,
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(11.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back),
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

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Servicios de este punto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(),
                    contentAlignment = Alignment.Center

                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,

                        ) {
                        Column(

                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(175.dp)
                                    .heightIn()
                                    .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Servicios básicos",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlackGray
                                    )

                                    ButtonService(
                                        text = "Agua potable",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Electricidad",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Baños públicos",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Duchas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Internet",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Lavandería",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Buena cobertura",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Mala Cobertura",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .width(175.dp)
                                    .heightIn()
                                    .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Servicios AC y campers",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlackGray
                                    )

                                    ButtonService(
                                        text = "Aguas grises",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Aguas negras",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Parcelas con sombra",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Suministro de gas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                }
                            }

                            Column(
                                modifier = Modifier
                                    .width(175.dp)
                                    .heightIn()
                                    .padding(start = 10.dp)
                                    .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Servicios para mascotas",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlackGray
                                    )

                                    ButtonService(
                                        text = "Se admiten mascotas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Instalacioens para perros",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )


                                }
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(175.dp)
                                    .heightIn()
                                    .padding(start = 10.dp)
                                    .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Instalaciones",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlackGray
                                    )

                                    ButtonService(
                                        text = "Piscina",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Zona Infantil",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Cafetería",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Restaurante",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Zona de barbacoa",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                    ButtonService(
                                        text = "Supermercado",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .width(175.dp)
                                    .heightIn()
                                    .padding(start = 10.dp)
                                    .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Alojamiento",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlackGray
                                    )

                                    ButtonService(
                                        text = "Parcelas de larga estancia",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Bungalows",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Cabañas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Zona de acampada",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                }
                            }

                            Column(
                                modifier = Modifier
                                    .width(175.dp)
                                    .heightIn()
                                    .padding(start = 10.dp)
                                    .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        start = 10.dp,
                                        top = 20.dp,
                                        bottom = 20.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Otros servicios",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = BlackGray
                                    )

                                    ButtonService(
                                        text = "Alquiler de bicicletas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Actividades infantiles",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Actividades acuáticas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                    ButtonService(
                                        text = "Excursiones guiadas",
                                        iconRes = R.drawable.ex,
                                        tint = Color.White,
                                        color = MainColor,
                                        onClick = { /*TODO*/ },
                                    )

                                }
                            }

                        }

                    }


                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(51.dp),

                        shape = RoundedCornerShape(20.dp),
                        color = MainColor,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {


                            Text(
                                text = "Siguiente",
                                modifier = Modifier.padding(top = 6.dp),
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                maxLines = 3,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }


            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),

                    shape = RoundedCornerShape(20.dp),
                    color = MainColor,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Text(
                            text = "Siguiente",
                            modifier = Modifier.padding(top = 6.dp),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun MapNewImageServiceMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .align(Alignment.TopStart)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            color = BackgroundColorList,
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(11.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back),
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

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sube fotos de este punto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sería de gran ayuda para la comunidad, que pudieras subir algunas fotos de este punto.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(149.dp),
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        MapNewRoutePointButton(
                            text = "Añadir fotos",
                            iconRes = R.drawable.plus,
                            tint = Color.White,
                            color = MainColor,
                            onClick = {
                            }
                        )

                        Spacer(
                            modifier = Modifier.height(13.dp),
                        )

                        MapNewRoutePointButton(
                            text = "Hacer fotos",
                            iconRes = R.drawable.camera,
                            tint = Color.White,
                            color = BackgroundButtonColor,
                            onClick = {
                            }
                        )
                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Tienes dudas de qué fotos subir?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Echa un vistazo a nusetras normas y\nrecomendaciones",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),

                    shape = RoundedCornerShape(20.dp),
                    color = MainColor,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Text(
                            text = "Subir imágenes",
                            modifier = Modifier.padding(top = 6.dp),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun MapNewLastDatesMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }

    var textValue by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .align(Alignment.TopStart)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            color = BackgroundColorList,
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(11.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back),
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

                }

                Box(
                    modifier = Modifier
                        .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Contacto del lugar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainColor
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CustomDropdown()

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomCheckBox(
                                isChecked = false,
                                onCheckedChange = {}
                            )


                            Surface(
                                color = MainColor,
                                modifier = Modifier.size(26.dp),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(12.5.dp)
                                            .height(14.29.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                    onDismiss()
                                                }
                                            },
                                        tint = Color.White
                                    )

                                }
                            }


                        }


                    }
                }

                Box(
                    modifier = Modifier
                        .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Precio por día",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainColor
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomCheckBox(
                                isChecked = false,
                                onCheckedChange = {}
                            )

                            Row(
                                modifier = Modifier
                                    .width(113.dp)
                                    .height(33.dp)
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BasicTextField(
                                    value = textValue,
                                    onValueChange = { textValue = it },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 4.dp),
                                    textStyle = TextStyle(
                                        fontSize = 12.sp,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = "Eur",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 4.dp, end = 4.dp)
                                )
                            }

                        }


                    }
                }

                Box(
                    modifier = Modifier
                        .background(WhiteGray, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .heightIn(),
                    contentAlignment = Alignment.TopStart
                ) {

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Horario",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "L",
                                onCheckedChange = {}
                            )
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "M",
                                onCheckedChange = {}
                            )
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "M",
                                onCheckedChange = {}
                            )
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "J",
                                onCheckedChange = {}
                            )
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "V",
                                onCheckedChange = {}
                            )
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "S",
                                onCheckedChange = {}
                            )
                            CustomCheckBoxDays(
                                isChecked = false,
                                day = "D",
                                onCheckedChange = {}
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomCheckBox(
                                isChecked = false,
                                onCheckedChange = {}
                            )
                            CustomCheckBox(
                                isChecked = false,
                                onCheckedChange = {}
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ){

                            CustomDropdownHour()
                            CustomDropdownHour()

                            Surface(
                                color = MainColor,
                                modifier = Modifier.size(26.dp),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(12.5.dp)
                                            .height(14.29.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                    onDismiss()
                                                }
                                            },
                                        tint = Color.White
                                    )

                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Meses de apertura",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainColor
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ){

                            CustomDropdownHour()
                            CustomDropdownHour()

                            Surface(
                                color = MainColor,
                                modifier = Modifier.size(26.dp),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(12.5.dp)
                                            .height(14.29.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomCheckBox(
                                isChecked = false,
                                onCheckedChange = {}
                            )
                            CustomCheckBox(
                                isChecked = false,
                                onCheckedChange = {}
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),

                    shape = RoundedCornerShape(20.dp),
                    color = MainColor,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Text(
                            text = "Saltar y crear punto",
                            modifier = Modifier.padding(top = 6.dp),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),

                    shape = RoundedCornerShape(20.dp),
                    color = MainColor,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Text(
                            text = "Guardar datos y crear punto",
                            modifier = Modifier.padding(top = 6.dp),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }
    }
}


@Composable
fun MapNewImageServiceUploadMenu(
    selectedPoint: LatLng?,
    selectedAddress: String?,
    onNameConfirmed: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val offsetY = remember { Animatable(600f) }
    var nameInput by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = tween(300))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .align(Alignment.TopStart)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            color = BackgroundColorList,
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(11.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrow_back),
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

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sube fotos de este punto",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sería de gran ayuda para la comunidad, que pudieras subir algunas fotos de este punto.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .background(BackgroundColorImage, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(244.dp),
                    contentAlignment = Alignment.TopStart

                ) {
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 10.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Surface(
                            modifier = Modifier
                                .width(100.dp)
                                .height(28.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color.White,


                            ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Foto de portada",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }

                        Surface(
                            color = Color.White,
                            modifier = Modifier.size(32.dp),
                            shape = RoundedCornerShape(11.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.points_more),
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
                                    tint = Color.Black
                                )

                            }
                        }

                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(BackgroundColorImage, shape = RoundedCornerShape(20.dp))
                            .width(170.dp)
                            .height(132.dp),
                        contentAlignment = Alignment.TopStart,

                        ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp, top = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.White,
                                modifier = Modifier.size(32.dp),
                                shape = RoundedCornerShape(11.dp),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.points_more),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(12.5.dp)
                                            .height(14.29.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                    onDismiss()
                                                }
                                            },
                                        tint = Color.Black
                                    )

                                }
                            }

                        }

                    }

                    Box(
                        modifier = Modifier
                            .background(BackgroundColorImage, shape = RoundedCornerShape(20.dp))
                            .width(170.dp)
                            .height(132.dp),
                        contentAlignment = Alignment.TopStart

                    ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp, top = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.White,
                                modifier = Modifier.size(32.dp),
                                shape = RoundedCornerShape(11.dp),
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.points_more),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(12.5.dp)
                                            .height(14.29.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                    onDismiss()
                                                }
                                            },
                                        tint = Color.Black
                                    )

                                }
                            }

                        }

                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(170.dp)
                            .height(132.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val pathEffect =
                                    PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)

                                drawRoundRect(
                                    color = Color.Gray,
                                    size = size,
                                    cornerRadius = CornerRadius(20.dp.toPx()),
                                    style = Stroke(width = strokeWidth, pathEffect = pathEffect)
                                )
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.White,
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.camera),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(28.dp)
                                            .height(28.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                    onDismiss()
                                                }
                                            },
                                        tint = BlackGray
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Hacer fotos",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = BlackGray,
                                        textAlign = TextAlign.Center
                                    )

                                }
                            }

                        }

                    }

                    Box(
                        modifier = Modifier
                            .width(170.dp)
                            .height(132.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val pathEffect =
                                    PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)

                                drawRoundRect(
                                    color = Color.Gray,
                                    size = size,
                                    cornerRadius = CornerRadius(20.dp.toPx()),
                                    style = Stroke(width = strokeWidth, pathEffect = pathEffect)
                                )
                            },
                        contentAlignment = Alignment.TopStart
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color.White,
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Cerrar",
                                        modifier = Modifier
                                            .width(28.dp)
                                            .height(28.dp)
                                            .clickable {
                                                scope.launch {
                                                    offsetY.animateTo(
                                                        600f,
                                                        animationSpec = tween(300)
                                                    )
                                                    onDismiss()
                                                }
                                            },
                                        tint = BlackGray
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Añadir fotos",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = BlackGray,
                                        textAlign = TextAlign.Center
                                    )

                                }
                            }

                        }

                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Tienes dudas de qué fotos subir?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Echa un vistazo a nusetras normas y\nrecomendaciones",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),

                    shape = RoundedCornerShape(20.dp),
                    color = MainColor,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Text(
                            text = "Subir imágenes",
                            modifier = Modifier.padding(top = 6.dp),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            textAlign = TextAlign.Center
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
    color: Color,
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


@Composable
fun ButtonCategory(
    text: String,
    iconRes: Int,
    tint: Color,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .width(64.dp)
            .height(64.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = color
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
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

@Composable
fun ButtonService(
    text: String,
    iconRes: Int,
    tint: Color,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Surface(
            onClick = onClick,
            modifier = Modifier
                .width(25.dp)
                .height(25.dp),
            shape = RoundedCornerShape(8.dp),
            color = color
        ) {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    modifier = Modifier.size(25.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row() {
            Text(
                text = text,
                modifier = Modifier.padding(top = 3.dp),
                fontSize = 10.sp,
                color = Color.Black
            )
        }

    }

}

enum class MapNewPointRoute {
    CREATE_ROUTE,
    CREATE_POINT

}

@Composable
fun CustomDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecciona una opción") }
    val opciones = listOf("Opción 1", "Opción 2", "Opción 3")

    var dropdownWidth by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    dropdownWidth = layoutCoordinates.size.width
                }
                .clickable { expanded = true }
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(14.dp))
                .background(Color.White, shape = RoundedCornerShape(14.dp))
                .padding(14.dp),
            color = Color.White,
            shape = RoundedCornerShape(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedOption, color = Color.LightGray)
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { dropdownWidth.toDp() })
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White, shape = RoundedCornerShape(14.dp))
        ) {

            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(text = opcion, color = Color.Black) },
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedOption = opcion
                        expanded = false
                    }
                )
            }


        }
    }
}

@Composable
fun CustomCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (isChecked) MainColor else Color.White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
                .clickable { onCheckedChange(!isChecked) },
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = "Sin contacto",
            modifier = Modifier
                .padding(12.dp),
            color = Color.Black
        )
    }

}

@Composable
fun CustomCheckBoxDays(
    isChecked: Boolean,
    day: String,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isChecked) MainColor else Color.White)
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .clickable { onCheckedChange(!isChecked) },
            contentAlignment = Alignment.Center
        ) {
            Text(text = day, color = Color.Black, fontSize = 12.sp)
        }

    }

}


@Composable
fun CustomDropdownHour() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Hora Apertura") }
    val opciones = listOf("Opción 1", "Opción 2", "Opción 3")

    var dropdownWidth by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.width(135.dp).height(33.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    dropdownWidth = layoutCoordinates.size.width
                }
                .clickable { expanded = true }
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color.White,
            shape = RoundedCornerShape(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedOption, color = Color.LightGray, fontSize = 10.sp)
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir",
                    tint = Color.LightGray
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { dropdownWidth.toDp() })
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(14.dp))
        ) {

            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(text = opcion, color = Color.Black, fontSize = 10.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedOption = opcion
                        expanded = false
                    }
                )
            }


        }
    }
}







@Preview(showBackground = true)
@Composable
fun MapNewImageServiceMenu() {
    var nameInput by remember { mutableStateOf("Punto de prueba") }
    MapNewLastDatesMenu(
        selectedPoint = LatLng(42.704, 0.106),
        selectedAddress = "C. Felipe Coscolla, 11, 22004 Huesca",
        onNameConfirmed = { name -> println("Nombre confirmado: $name") },
        onDismiss = { println("Menú cerrado") }
    )
}