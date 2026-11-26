package com.example.huellitas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huellitas.domain.model.Perro
import com.example.huellitas.domain.repository.PerroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerroViewModel @Inject constructor(
    private val repository: PerroRepository
) : ViewModel() {

    val perros: StateFlow<List<Perro>> = repository.obtenerPerros()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun agregarPerro(perro: Perro) {
        if (perro.nombre.isBlank()) return
        viewModelScope.launch { repository.insertarPerro(perro.copy(nombre = perro.nombre.trim())) }
    }

    fun actualizarPerro(perro: Perro) {
        viewModelScope.launch { repository.actualizarPerro(perro) }
    }

    fun eliminarPerro(perro: Perro) {
        viewModelScope.launch { repository.eliminarPerro(perro) }
    }
}
