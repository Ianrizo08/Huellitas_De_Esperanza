package com.example.huellitas.data.sesion

import android.content.Context
import com.example.huellitas.domain.model.Usuario
import com.example.huellitas.domain.repository.UsuarioRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SesionManager @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: UsuarioRepository
) {
    private val preferencias = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario.asStateFlow()

    init {
        val idGuardado = preferencias.getInt(CLAVE_ID, SIN_SESION)
        if (idGuardado != SIN_SESION) {
            CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                val recuperado = repository.obtenerUsuario(idGuardado)
                if (recuperado == null) {
                    preferencias.edit().remove(CLAVE_ID).apply()
                } else {
                    _usuario.value = recuperado
                }
            }
        }
    }

    fun iniciar(usuario: Usuario) {
        preferencias.edit().putInt(CLAVE_ID, usuario.id).apply()
        _usuario.value = usuario
    }

    fun cerrar() {
        preferencias.edit().remove(CLAVE_ID).apply()
        _usuario.value = null
    }

    private companion object {
        const val CLAVE_ID = "id_usuario"
        const val SIN_SESION = -1
    }
}
