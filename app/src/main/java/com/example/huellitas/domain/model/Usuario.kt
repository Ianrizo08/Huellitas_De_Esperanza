package com.example.huellitas.domain.model

enum class RolUsuario { ADOPTANTE, VOLUNTARIO, ADMIN }

data class Usuario(
    val id: Int = 0,
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val contrasenaHash: String = "",
    val rol: RolUsuario = RolUsuario.ADOPTANTE,
    val fechaRegistro: Long = System.currentTimeMillis()
)
