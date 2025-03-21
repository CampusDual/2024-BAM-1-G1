package com.vango.presentation.main.menu.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vango.R
import com.vango.presentation.theme.StyledButton
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.vango.R.drawable
import com.vango.R.drawable.ic_edit
import com.vango.presentation.theme.BackgroundButtonColor
import com.vango.presentation.theme.BackgroundUnselected

@Composable
fun MyDataContent(navController: NavController) {
    // Estados para controlar el modo de edición de cada bloque
    var isEditingAccount by remember { mutableStateOf(false) }
    var isEditingPersonal by remember { mutableStateOf(false) }
    var isEditingVehicle by remember { mutableStateOf(false) }

    // Datos simulados (puedes reemplazarlos con datos reales)
    var profilePictureUri by remember { mutableStateOf<String?>(null) }
    var username by remember { mutableStateOf("usuario123") }
    var password by remember { mutableStateOf("********") }

    var fullName by remember { mutableStateOf("John Doe") }
    var birthDate by remember { mutableStateOf("01/01/1990") }
    var email by remember { mutableStateOf("john.doe@example.com") }
    var phone by remember { mutableStateOf("+1 234 567 890") }
    var country by remember { mutableStateOf("España") }
    var province by remember { mutableStateOf("Madrid") }

    var vehicleType by remember { mutableStateOf("Furgoneta") }
    var vehicleModel by remember { mutableStateOf("Toyota") }
    var vehicleYear by remember { mutableStateOf("2020") }
    var vehicleLength by remember { mutableStateOf("5,3") }
    var vehicleWidth by remember { mutableStateOf("2,5") }
    var vehicleHeight by remember { mutableStateOf("1,8") }
    var vehicleWeight by remember { mutableStateOf("1000") }

    val ImageSelectlauncher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            profilePictureUri = uri.toString()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bloque 1: Datos de Cuenta
        item {
            DataBlock(
                title = "Datos de Cuenta",
                isEditing = isEditingAccount,
                onEditClick = { isEditingAccount = !isEditingAccount },
                onSaveClick = {
                    // Guardar cambios aquí
                    isEditingAccount = false
                }
            ) {
                DataRowImage(
                    label = "Foto de perfil",
                    imageUri = profilePictureUri,
                    isEditing = isEditingAccount
                ) {
                    ImageSelectlauncher.launch("image/*")
                }
                DataRow(
                    label = "Usuario",
                    value = username,
                    isEditing = isEditingAccount
                ) { newValue ->
                    username = newValue
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contraseña",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = password,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = if (isEditingAccount) {
                            Modifier.clickable {
                                navController.navigate("change_password")
                            }
                        } else {
                            Modifier
                        }
                    )
                }
            }
        }

        // Bloque 2: Datos Personales
        item {
            DataBlock(
                title = "Datos Personales",
                isEditing = isEditingPersonal,
                onEditClick = { isEditingPersonal = !isEditingPersonal },
                onSaveClick = {
                    // Guardar cambios aquí
                    isEditingPersonal = false
                }
            ) {
                DataRow(
                    label = "Nombre y Apellidos",
                    value = fullName,
                    isEditing = isEditingPersonal
                ) { newValue ->
                    fullName = newValue
                }
                DataRow(
                    label = "Fecha de nacimiento",
                    value = birthDate,
                    isEditing = isEditingPersonal
                ) { newValue ->
                    birthDate = newValue
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Correo electrónico",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = email,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = if (isEditingPersonal) {
                            Modifier.clickable {
                                navController.navigate("change_email")
                            }
                        } else {
                            Modifier
                        }
                    )
                }
                DataRow(
                    label = "Teléfono",
                    value = phone,
                    isEditing = isEditingPersonal
                ) { newValue ->
                    phone = newValue
                }
                DataRow(
                    label = "País",
                    value = country,
                    isEditing = isEditingPersonal
                ) { newValue ->
                    country = newValue
                }
                DataRow(
                    label = "Provincia",
                    value = province,
                    isEditing = isEditingPersonal
                ) { newValue ->
                    province = newValue
                }
            }
        }

        // Bloque 3: Datos de Vehículo
        item {
            DataBlock(
                title = "Datos de Vehículo",
                isEditing = isEditingVehicle,
                onEditClick = { isEditingVehicle = !isEditingVehicle },
                onSaveClick = {
                    // Guardar cambios aquí
                    isEditingVehicle = false
                }
            ) {
                DataRow(
                    label = "Tipo de vehiculo",
                    value = vehicleType,
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleType = newValue
                }
                DataRow(
                    label = "Modelo",
                    value = vehicleModel,
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleModel = newValue
                }
                DataRow(
                    label = "Año",
                    value = vehicleYear,
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleYear = newValue
                }
                DataRowWithUnit(
                    label = "Largo",
                    value = vehicleLength,
                    unit = "m",
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleLength = newValue
                }
                DataRowWithUnit(
                    label = "Ancho",
                    value = vehicleWidth,
                    unit = "m",
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleWidth = newValue
                }
                DataRowWithUnit(
                    label = "Altura",
                    value = vehicleHeight,
                    unit = "m",
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleHeight = newValue
                }
                DataRowWithUnit(
                    label = "Peso",
                    value = vehicleWeight,
                    unit = "kg",
                    isEditing = isEditingVehicle
                ) { newValue ->
                    vehicleWeight = newValue
                }

            }
        }
    }
}


@Composable
fun DataBlock(
    title: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray.copy(alpha = 0.3f)) // Fondo gris claro
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Título y botón de edición
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = drawable.ic_edit),
                    contentDescription = "Editar",
                    tint = if (isEditing) {
                        BackgroundButtonColor
                    } else {
                        BackgroundUnselected
                    }
                )
            }
        }

        // Contenido del bloque
        content()

        // Botón Guardar (visible solo en modo edición)
        if (isEditing) {
            StyledButton(
                isSelected = true,
                text = "Guardar",
                onClick = onSaveClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataRow(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
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
        if (isEditing) {
            TextField(
                value, onValueChange, Modifier
                    .width(200.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                // Fondo blanco con bordes redondeados
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    errorTextColor = Color.Red,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        } else {
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun DataRowImage(
    label: String,
    imageUri: String?, // URI de la imagen seleccionada
    isEditing: Boolean,
    onImageChange: () -> Unit
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
        if (isEditing) {
            IconButton(
                onClick = onImageChange,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = drawable.default_image_profile), // Ícono de edición
                    contentDescription = "Seleccionar imagen",
                    tint = Color.Unspecified // Mantener el color original del ícono
                )
            }
        } else {
            if (imageUri.isNullOrEmpty()) {
                // Mostrar un placeholder si no hay imagen seleccionada
                Image(
                    painter = painterResource(id = drawable.default_image_profile),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Mostrar la imagen cargada desde la URI
                AsyncImage(
                    model = imageUri, // URI de la imagen seleccionada
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataRowWithUnit(
    label: String,
    value: String,
    unit: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label ($unit)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        if (isEditing) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                singleLine = true,
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        } else {
            Text(
                text = "$value $unit",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun myDataContentPreview() {
    val navController = rememberNavController()
    MyDataContent(navController = navController)
}