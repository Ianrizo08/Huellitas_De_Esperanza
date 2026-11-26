package com.example.huellitas.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.example.huellitas.data.database.dao.UsuarioDao
import com.example.huellitas.data.mapper.toDomain
import com.example.huellitas.data.mapper.toEntity
import com.example.huellitas.data.security.PasswordHasher
import com.example.huellitas.domain.model.RolUsuario
import com.example.huellitas.domain.model.Usuario
import com.example.huellitas.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepositoryImp @Inject constructor(
    private val usuarioDao: UsuarioDao
) : UsuarioRepository {

    override fun obtenerUsuarios(): Flow<List<Usuario>> =
        usuarioDao.obtenerUsuarios().map { lista -> lista.map { it.toDomain() } }

    override suspend fun registrar(
        nombre: String,
        correo: String,
        telefono: String,
        contrasena: String,
        rol: RolUsuario
    ): Result<Unit> {
        val usuario = Usuario(
            nombre = nombre.trim(),
            correo = correo.trim().lowercase(),
            telefono = telefono.trim(),
            contrasenaHash = PasswordHasher.hash(contrasena),
            rol = rol
        )
        return try {
            usuarioDao.insertarUsuario(usuario.toEntity())
            Result.success(Unit)
        } catch (e: SQLiteConstraintException) {
            Result.failure(e)
        } catch (e: IllegalArgumentException) {
            Result.failure(e)
        }
    }

    override suspend fun iniciarSesion(correo: String, contrasena: String): Usuario? {
        val usuario = usuarioDao.buscarPorCorreo(correo.trim().lowercase()) ?: return null
        return if (PasswordHasher.verificar(contrasena, usuario.contrasenaHash)) usuario.toDomain() else null
    }

    override suspend fun obtenerUsuario(id: Int): Usuario? =
        usuarioDao.buscarPorId(id)?.toDomain()

    override suspend fun actualizarUsuario(usuario: Usuario) =
        usuarioDao.actualizarUsuario(usuario.toEntity())

    override suspend fun eliminarUsuario(usuario: Usuario) =
        usuarioDao.eliminarUsuario(usuario.toEntity())
}
