package com.vango.presentation.main.home.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
fun MapNewRoutePointMenu(
    selectedRoutePoint: MapNewPointRoute?,
    onLayerSelected: (MapNewPointRoute) -> Unit,
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
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "¿Qué quieres crear?",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
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


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MapNewRoutePointButton(
                            text = "Crear Ruta",
                            iconRes = R.drawable.ruta,
                            tint = BackgroundButtonColor,
                            onClick = {
                            }
                        )

                        Spacer(modifier = Modifier.width(60.dp))

                        MapNewRoutePointButton(
                            text = "Crear Punto",
                            iconRes = R.drawable.marker,
                            tint = Color.Black,

                            onClick = {
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

            shape = RoundedCornerShape(20.dp),
            color = Color.Transparent,
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = text,
                    modifier = Modifier.size(36.dp),
                    tint = tint
                )
            }
        }
        Text(
            text = text,
            modifier = Modifier.padding(top = 6.dp),
            fontSize = 12.sp,
            color = Color.Black,
            maxLines = 3,
            textAlign = TextAlign.Center
        )
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