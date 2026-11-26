package com.example.huellitas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huellitas.data.sesion.SesionManager
import com.example.huellitas.domain.model.RolUsuario
import com.example.huellitas.domain.model.Usuario
import com.example.huellitas.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsuarioViewModel @Inject constructor(
    private val repository: UsuarioRepository,
    private val sesion: SesionManager
) : ViewModel() {

    val usuarios: StateFlow<List<Usuario>> = repository.obtenerUsuarios()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    val usuarioActual: StateFlow<Usuario?> = sesion.usuario

    fun registrar(
        nombre: String,
        correo: String,
        telefono: String,
        contrasena: String,
        rol: RolUsuario = RolUsuario.ADOPTANTE,
        onResultado: (Result<Unit>) -> Unit = {}
    ) {
        if (nombre.isBlank() || correo.isBlank() || contrasena.isBlank()) return
        viewModelScope.launch {
            val resultado = repository.registrar(nombre, correo, telefono, contrasena, rol)
            if (resultado.isSuccess) {
                repository.iniciarSesion(correo, contrasena)?.let { sesion.iniciar(it) }
            }
            onResultado(resultado)
        }
    }

    fun iniciarSesion(correo: String, contrasena: String, onResultado: (Usuario?) -> Unit = {}) {
        viewModelScope.launch {
            val usuario = repository.iniciarSesion(correo, contrasena)
            if (usuario != null) sesion.iniciar(usuario)
            onResultado(usuario)
        }
    }

    fun cerrarSesion() {
        sesion.cerrar()
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch { repository.eliminarUsuario(usuario) }
    }
}
