package com.example.huellitas.data.repository

import com.example.huellitas.data.database.dao.PerroDao
import com.example.huellitas.data.mapper.toDomain
import com.example.huellitas.data.mapper.toEntity
import com.example.huellitas.domain.model.Perro
import com.example.huellitas.domain.repository.PerroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerroRepositoryImp @Inject constructor(
    private val perroDao: PerroDao
) : PerroRepository {

    override fun obtenerPerros(): Flow<List<Perro>> =
        perroDao.obtenerPerros().map { lista -> lista.map { it.toDomain() } }

    override fun obtenerPerro(id: Int): Flow<Perro?> =
        perroDao.getPerro(id).map { it?.toDomain() }

    override suspend fun insertarPerro(perro: Perro) = perroDao.insertarPerro(perro.toEntity())

    override suspend fun actualizarPerro(perro: Perro) = perroDao.actualizarPerro(perro.toEntity())

    override suspend fun eliminarPerro(perro: Perro) = perroDao.eliminarPerro(perro.toEntity())
}
