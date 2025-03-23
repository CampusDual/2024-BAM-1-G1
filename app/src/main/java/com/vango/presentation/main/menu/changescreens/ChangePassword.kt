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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
fun ChangePasswordScreen(
    navController: NavController,
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
                text = "Mis Datos:Contraseña",
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
                    text = "Cambiar contraseña",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextColor
                )
                // Campo para la contraseña anterior
                Text(
                    text = "Contraseña Anterior",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextColor
                )
                TextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        errorTextColor = Color.Red
                    ),
//                    colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
//                        containerColor = Color.White,
//                        focusedTextColor = TextColor,
//                        errorTextColor = Color.Red,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    )
                )

                // Campo para la nueva contraseña
                Text(
                    text = "Nueva Contraseña",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextColor
                )
                TextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        errorTextColor = Color.Red
                    ),
//                    colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
//                        containerColor = Color.White,
//                        focusedTextColor = TextColor,
//                        errorTextColor = Color.Red,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    )
                )

                // Campo para repetir la nueva contraseña
                Text(
                    text = "Repetir Nueva Contraseña",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextColor
                )
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        errorTextColor = Color.Red
                    ),
//                    colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
//                        containerColor = Color.White,
//                        focusedTextColor = TextColor,
//                        errorTextColor = Color.Red,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent
//                    )
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
                            .clickable {
                                // Aquí puedes validar las contraseñas y realizar el cambio
                                if (newPassword == confirmPassword) {
                                    println("Contraseña cambiada: $newPassword")
                                } else {
                                    println("Las contraseñas no coinciden")
                                }
                            }
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
fun ChangePaswordPreview() {
    val navController = rememberNavController()
    ChangePasswordScreen(navController = navController)
}