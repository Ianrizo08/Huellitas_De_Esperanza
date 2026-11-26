package com.example.huellitas.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.huellitas.R
import com.example.huellitas.data.AppScreens

private data class OpcionNavegacion(
    val ruta: String,
    val textoRes: Int,
    val icono: ImageVector
)

private val opciones = listOf(
    OpcionNavegacion(AppScreens.PrimeraPantalla.route, R.string.nav_inicio, Icons.Filled.Home),
    OpcionNavegacion(AppScreens.SegundaPantalla.route, R.string.nav_adoptar, Icons.Filled.Favorite),
    OpcionNavegacion(AppScreens.Apoyar.route, R.string.nav_apoyar, Icons.Filled.Add),
    OpcionNavegacion(AppScreens.Cuenta.route, R.string.nav_cuenta, Icons.Filled.AccountCircle)
)

@Composable
fun BarraNavegacion(navController: NavController) {
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        opciones.forEach { opcion ->
            NavigationBarItem(
                selected = opcion.ruta == rutaActual,
                onClick = {
                    if (opcion.ruta != rutaActual) {
                        navController.navigate(opcion.ruta) {
                            popUpTo(AppScreens.PrimeraPantalla.route)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(opcion.icono, contentDescription = null) },
                label = { Text(stringResource(opcion.textoRes), style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
