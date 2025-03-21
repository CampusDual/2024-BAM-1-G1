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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vango.R
import com.vango.presentation.theme.BackgroundColorButtonPrincipal

@Composable
fun MenuScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fila con el botón de cerrar y el título
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Cerrar",
                    tint = Color.Unspecified
                )
            }
            Text(
                text = "Menú",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(40.dp)) // Espacio reservado para equilibrar
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Columna central con la foto, nick y tipo de cuenta
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box() {
                Image(
                    painter = painterResource(id = R.drawable.default_image_profile), // Reemplaza con tu imagen
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                IconButton(
                    onClick = { navController.navigate("change_profile_picture") },
                    modifier = Modifier.size(27.dp).align( Alignment.BottomEnd)
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_change_profile_image),
                        contentDescription = "Editar",
                        tint = Color.Unspecified

                    )
                }
            }




            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Usuario123", // Nick del usuario
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Premium", // O "Cuenta Free" según corresponda
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic,
                fontSize = 10.sp,
                color = BackgroundColorButtonPrincipal
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            val options = listOf(
                "Mi cuenta" to "profile",
                "Notificaciones" to "notifications",
                "Mis Favoritos" to "favorites",
                "Ajustes" to "settings",
                "Soporte" to "support",
                "Desconectarse" to "logout"
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
                            "profile" -> painterResource(id = R.drawable.ic_profile)
                            "notifications" -> painterResource(id = R.drawable.ic_notifications)
                            "favorites" -> painterResource(id = R.drawable.ic_favorites)
                            "settings" -> painterResource(id = R.drawable.ic_settings)
                            "support" -> painterResource(id = R.drawable.ic_support)
                            "logout" -> painterResource(id = R.drawable.ic_logout)
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
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )

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
fun PreviewUserProfileScreen() {
    val navController = rememberNavController()
    MenuScreen(
        navController = navController
    )
}