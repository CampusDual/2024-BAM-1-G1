package com.vango.presentation.main.menu.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vango.R


// Componente para mostrar el contenido de "Premium"
@Composable
fun PremiumContent(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bloque 1: Suscripción
        item {
            DataBlock(title = "Suscripción") {
                HighlightedRow2 {
                    DataRow(label = "Versión", value = "Premium - Suscripción Anual")
                }
                HighlightedRow2 {
                    DataRow(label = "Válido hasta", value = "30 Mayo 2025")
                }
                HighlightedRow2 {
                    DataRow(label = "Precio", value = "9,99€/año")
                }
            }
        }

        // Bloque 2: Funcionalidades Incluidas
        item {
            DataBlock(title = "Funcionalidades Incluidas") {
                HighlightedRow2 {
                    FeatureRow(
                        imageResId = R.drawable.ic_launcher_foreground,
                        title = "Crear rutas infinitas",
                        description = "Crea tu ruta personalizada de forma segura"

                    ){
                        navController.navigate("support")
                    }
                }
                HighlightedRow2 {
                    FeatureRow(
                        imageResId = R.drawable.ic_launcher_foreground,
                        title = "Perfil personalizable",
                        description = "Personaliza tu perfil con fotos y datos"
                    ){
                        navController.navigate("support")
                    }
                }
                HighlightedRow2 {
                    FeatureRow(
                        imageResId = R.drawable.ic_launcher_foreground,
                        title = "Soporte prioritario",
                        description = "Accede a soporte técnico prioritario"
                    )
                    {
                        navController.navigate("support")
                    }
                }
                HighlightedRow2 {
                    FeatureRow(
                        imageResId = R.drawable.ic_launcher_foreground,
                        title = "Soporte prioritario",
                        description = "Accede a soporte técnico prioritario"
                    )
                    {
                        navController.navigate("support")
                    }
                }
                HighlightedRow2 {
                    FeatureRow(
                        imageResId = R.drawable.ic_launcher_foreground,
                        title = "Soporte prioritario",
                        description = "Accede a soporte técnico prioritario"
                    )
                    {
                        navController.navigate("support")
                    }
                }
            }
        }
    }
}

@Composable
fun DataBlock(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray.copy(alpha = 0.3f)) // Fondo gris claro
            .padding(vertical = 12.dp, horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Título del bloque
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 6.dp)
        )

        // Contenido del bloque
        content()
    }
}

@Composable
fun HighlightedRow2(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.6f), shape = RoundedCornerShape(15.dp))
            .padding(vertical = 10.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
fun DataRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun FeatureRow(
    imageResId: Int,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun mySubscriptionContentPreview() {
    var navController = rememberNavController()
    PremiumContent(navController = navController)
}


