package com.vango.presentation.main.menu.settings

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vango.R
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorList
import com.vango.presentation.theme.TextColor

@Composable
fun SettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .heightIn()
            .padding(20.dp, 55.dp, 20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                color = BackgroundColorList,
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(14.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "Atras",
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                            .rotate(180f),
                        tint = Color.White
                    )

                }
            }
            Column(
                Modifier
                    .widthIn()
                    .height(40.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Ajustes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
            }
            Surface(
                color = BackgroundButtonColor,
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(14.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ex),
                        contentDescription = "Cerrar",
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp)
                            .clickable {
                                navController.navigate("home")
                            },
                        tint = Color.White
                    )

                }
            }

        }
        Spacer(modifier = Modifier.height(18.dp))
        Box(modifier = Modifier.align(Alignment.Start)) {
            Text(
                text = "Configuración",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            val options = listOf(
                "Filtros predeterminados" to "default_filters",
                "Seleccionar idioma" to "language",
                "Descargar mapas sin conexión" to "offline_maps",
                "Subir fotos solo con Wi-fi" to "wifi_only",
                "Accesibilidad" to "accessibility"
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 0.dp),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.2f)
            )
            options.forEach { (title, route) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(route) }
                        .padding(vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = when (route) {
                            "default filters" -> painterResource(id = R.drawable.ic_default_filters)
                            "language" -> painterResource(id = R.drawable.ic_language)
                            "offline_maps" -> painterResource(id = R.drawable.ic_offline_maps)
                            "wifi_only" -> painterResource(id = R.drawable.ic_wifi)
                            "accessibility" -> painterResource(id = R.drawable.ic_accessibility)
                            else -> painterResource(id = R.drawable.ic_profile)
                        },
                        contentDescription = null,
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black), // Opcional: Aplicar un color
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Normal
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit_password),
                            contentDescription = "Atras",
                            modifier = Modifier
                                .width(10.dp)
                                .height(10.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(TextColor)
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 0.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.2f)
                )

            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Box(modifier = Modifier.align(Alignment.Start)) {
            Text(
                text = "Legal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            val options = listOf(
                "Términos y condiciones" to "terms",
                "Política de privacidad" to "privacy_policy",
                "Licencia de código abierto" to "open_source_license"
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 0.dp),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.2f)
            )
            options.forEach { (title, route) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(route) }
                        .padding(vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = when (route) {
                            "terms" -> painterResource(id = R.drawable.ic_terms)
                            "privacy_policy" -> painterResource(id = R.drawable.ic_terms)
                            "open_source_license" -> painterResource(id = R.drawable.ic_terms)
                            else -> painterResource(id = R.drawable.ic_profile)
                        },
                        contentDescription = null,
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(TextColor), // Opcional: Aplicar un color
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = TextColor,
                        fontWeight = FontWeight.Normal
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit_password),
                            contentDescription = "Atras",
                            modifier = Modifier
                                .width(10.dp)
                                .height(10.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(TextColor)
                        )
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 0.dp),
                    thickness = 1.dp,
                    color = Color.Gray.copy(alpha = 0.2f)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RoutesScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(navController = navController)
}