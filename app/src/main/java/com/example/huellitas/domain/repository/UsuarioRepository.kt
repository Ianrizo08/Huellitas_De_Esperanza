package com.example.huellitas.domain.repository

import com.example.huellitas.domain.model.RolUsuario
import com.example.huellitas.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    fun obtenerUsuarios(): Flow<List<Usuario>>

    suspend fun registrar(
        nombre: String,
        correo: String,
        telefono: String,
        contrasena: String,
        rol: RolUsuario = RolUsuario.ADOPTANTE
    ): Result<Unit>

    suspend fun iniciarSesion(correo: String, contrasena: String): Usuario?

    suspend fun obtenerUsuario(id: Int): Usuario?

    suspend fun actualizarUsuario(usuario: Usuario)
    suspend fun eliminarUsuario(usuario: Usuario)
}
