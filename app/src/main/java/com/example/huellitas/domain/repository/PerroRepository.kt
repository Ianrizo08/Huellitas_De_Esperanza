package com.example.huellitas.domain.repository

import com.example.huellitas.domain.model.Perro
import kotlinx.coroutines.flow.Flow

interface PerroRepository {
    fun obtenerPerros(): Flow<List<Perro>>
    fun obtenerPerro(id: Int): Flow<Perro?>
    suspend fun insertarPerro(perro: Perro)
    suspend fun actualizarPerro(perro: Perro)
    suspend fun eliminarPerro(perro: Perro)
}
