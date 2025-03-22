package com.vango.presentation.main.menu.changescreens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vango.R
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundColorCard
import com.vango.presentation.theme.BackgroundColorList
import com.vango.presentation.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeEmailScreen(
    navController: NavController,
) {
    var newEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .heightIn()
            .padding(20.dp, 55.dp, 20.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
                    text = "Mis Datos:Correo electrónico",
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

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(BackgroundColorCard) // Fondo gris claro
                .padding(vertical = 12.dp, horizontal = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Cambiar correo electrónico",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            // Campo para la contraseña anterior
            Text(
                text = "Nuevo correo electrónico",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor
            )
            TextField(
                value = newEmail,
                onValueChange = { newEmail = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedTextColor = TextColor,
                    errorTextColor = Color.Red,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Campo para la nueva contraseña
            Text(
                text = "Introduce la Contraseña",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedTextColor = TextColor,
                    errorTextColor = Color.Red,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )



            // Botón para cambiar la contraseña
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = BackgroundButtonColor,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(48.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Cambiar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangeEmailPreview() {
    val navController = rememberNavController()
    ChangeEmailScreen(navController = navController)
}