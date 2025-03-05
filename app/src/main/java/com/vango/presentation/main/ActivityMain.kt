package com.vango.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.vango.presentation.base.BaseActivity
import com.vango.presentation.main.favorites.FavoritesScreen
import com.vango.presentation.main.home.HomeScreen
import com.vango.presentation.main.profile.ProfileScreen
import com.vango.presentation.main.results.HomeList
import com.vango.presentation.main.routes.RoutesScreen
import com.vango.presentation.main.travels.TravelsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain :  BaseActivity() {
    private lateinit var viewModel: ActivityMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[ActivityMainViewModel::class.java]

        setContent {
            HomeContent(viewModel)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeContent(viewModel: ActivityMainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    val systemBarsPadding = WindowInsets.systemBars.asPaddingValues()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onItemSelected = { route ->
                    if (route != currentRoute) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets(0, 0, 0, 0))
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(customPadding(innerPadding, systemBarsPadding))
                .fillMaxSize()
        ) {
            composable("home") {
                HomeScreen(
                    modifier = Modifier.padding(innerPadding), // Modifier para respetar el padding del Scaffold
                    navController = navController, // Pasar el NavController real
                    cameraPositionState = null, // Usar el valor por defecto de HomeScreen
                    permissionState = null, // Usar el valor por defecto de HomeScreen
                    isPreview = false // No es una previsualización
                )
            }
            composable("routes") {
                RoutesScreen()
            }
            composable("travels") {
                TravelsScreen()
            }
            composable("favorites") {
                FavoritesScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
            composable("results") {
                HomeList(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(currentRoute: String, onItemSelected: (String) -> Unit, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp + WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()),
        tonalElevation = 0.dp
    ) {

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { onItemSelected("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Routes") },
            label = { Text("Routes") },
            selected = currentRoute == "routes",
            onClick = { onItemSelected("routes") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Travels") },
            label = { Text("Travels") },
            selected = currentRoute == "travels",
            onClick = { onItemSelected("travels") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = currentRoute == "favorites",
            onClick = { onItemSelected("favorites") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { onItemSelected("profile") }
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val mockViewModel = ActivityMainViewModel()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "home",
                onItemSelected = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets(0, 0, 0, 0))
            )
        }
    ) { paddingValues ->
        HomeScreen(
            modifier = Modifier.padding(paddingValues),
            navController = rememberNavController(),
            cameraPositionState = null,
            permissionState = null,
            isPreview = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(
        currentRoute = "home",
        onItemSelected = { },
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets(0, 0, 0, 0))
    )
}

@Composable
private fun customPadding(innerPadding: PaddingValues, systemBarsPadding: PaddingValues): PaddingValues {
    return PaddingValues(
        top = 0.dp,
        bottom = innerPadding.calculateBottomPadding(),
        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
    )
}