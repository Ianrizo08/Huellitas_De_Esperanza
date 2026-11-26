package com.example.huellitas.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.huellitas.data.AppScreens

@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.PrimeraPantalla.route
    ) {
        composable(route = AppScreens.PrimeraPantalla.route) {
            PrimeraPantalla(navController)
        }
        composable(route = AppScreens.SegundaPantalla.route) {
            SegundaPantalla(navController)
        }
        composable(route = AppScreens.Apoyar.route) {
            ApoyarPantalla(navController)
        }
        composable(route = AppScreens.Cuenta.route) {
            CuentaPantalla(navController)
        }
        composable(route = AppScreens.NuevoPerro.route) {
            NuevoPerroPantalla(navController)
        }
        composable(
            route = AppScreens.PerfilPerro.route,
            arguments = listOf(
                navArgument(AppScreens.PerfilPerro.ARGUMENTO_ID) { type = NavType.IntType }
            )
        ) { entrada ->
            PerfilPerroPantalla(
                navController = navController,
                idPerro = entrada.arguments?.getInt(AppScreens.PerfilPerro.ARGUMENTO_ID) ?: 0
            )
        }
    }
}
