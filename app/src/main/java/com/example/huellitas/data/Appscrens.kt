package com.example.huellitas.data

sealed class AppScreens(val route: String) {
    object PrimeraPantalla : AppScreens("Inicio")
    object SegundaPantalla : AppScreens("Catalogo")
    object Apoyar : AppScreens("Apoyar")
    object Cuenta : AppScreens("Cuenta")
    object NuevoPerro : AppScreens("NuevoPerro")
    object PerfilPerro : AppScreens("Perfil/{id}") {
        const val ARGUMENTO_ID = "id"
        fun crearRuta(id: Int): String = "Perfil/$id"
    }
}
