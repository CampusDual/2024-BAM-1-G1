package com.vango.presentation.main.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorBadge
import com.vango.presentation.theme.BackgroundUnselected

@Composable
fun FilterMenu(
    modifier: Modifier = Modifier,
    onFiltersChanged: (Set<Int>) -> Unit
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

    val selectedTypes = remember(isCampingSelected, isParkingSelected, isHospitalSelected, isFuelStationSelected, isLaundrySelected) {
        buildSet {
            if (isCampingSelected) add(0)
            if (isParkingSelected) add(1)
            if (isHospitalSelected) add(2)
            if (isFuelStationSelected) add(3)
            if (isLaundrySelected) add(4)
        }
    }

    LaunchedEffect(selectedTypes) {
        onFiltersChanged(selectedTypes)
    }


    Box(
        modifier = modifier
            .clickable(
                enabled = isExpanded,
                onClick = { isExpanded = false },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .then(if (isExpanded) Modifier.fillMaxSize() else Modifier)
    ) {
        Surface(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = BackgroundButtonColor
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = if (isExpanded || selectedCount > 0) painterResource(id = R.drawable.filter_fill) else painterResource(
                        id = R.drawable.filter_no_fill
                    ),
                    contentDescription = "Filtros",
                    modifier = Modifier.size(27.dp),
                    tint = Color.White
                )
                Text(
                    text = "Filtros",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }

        if (selectedCount > 0) {
            Surface(
                modifier = Modifier
                    .offset(x = 40.dp, y = -10.dp)
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
                .offset(y = 70.dp)
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
        color = if (isSelected) BackgroundButtonColor else BackgroundUnselected
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

//@Preview(showBackground = true)
//@Composable
//fun FilterMenuPreviewClosed() {
//    FilterMenu(
//        modifier = Modifier.offset(y = 120.dp)
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun FilterMenuPreviewExpanded() {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//    ) {
//        FilterMenu(
//            modifier = Modifier.offset(y = 120.dp)
//        )
//    }
//}