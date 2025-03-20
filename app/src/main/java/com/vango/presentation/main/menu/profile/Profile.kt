package com.vango.presentation.main.menu.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vango.presentation.base.BaseActivity
import com.vango.presentation.main.menu.changescreens.ChangeEmailScreen
import com.vango.presentation.main.menu.changescreens.ChangePasswordScreen
import com.vango.presentation.theme.StyledButton
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.composable

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    // Estado para controlar qué contenido mostrar
   var selectedSection by remember { mutableStateOf("mis_datos") }

    // NavController anidado para manejar las rutas secundarias
    val nestedNavController = rememberNavController()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }, // Ir hacia atrás
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Ir atrás",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Mi Cuenta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = { navController.navigate("home") }, // Ir a Home
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.Black
                    )
                }
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Botones de navegación interna ("Mis datos", "Premium", "Aportaciones")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StyledButton(
                        text = "Mis datos",
                        onClick = { selectedSection = "mis_datos" },
                        isSelected = selectedSection == "mis_datos",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    StyledButton(
                        text = "Premium",
                        onClick = { selectedSection = "premium" },
                        isSelected = selectedSection == "premium",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    StyledButton(
                        text = "Aportaciones",
                        onClick = { selectedSection = "aportaciones" },
                        isSelected = selectedSection == "aportaciones",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Contenido dinámico según el botón seleccionado
                when (selectedSection) {
                    "mis_datos" -> {
                        ProfileScreenNavHost(navController = nestedNavController)
                    }

                    "premium" -> PremiumContent()
                    "aportaciones" -> ContributionsContent()
                }
            }
        }
    )
}

@Composable
fun ProfileScreenNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "profile"
    ) {
        composable("profile") {
            MyDataContent(navController = navController)
        }
        composable("change_password") {
            ChangePasswordScreen(navController = navController)
        }
        composable("change_email") {
            ChangeEmailScreen(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutesScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(
        navController = navController
    )
}