package com.example.huellitas.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.huellitas.data.database.entity.PerroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PerroDao {
    @Insert
    suspend fun insertarPerro(perro: PerroEntity)

    @Update
    suspend fun actualizarPerro(perro: PerroEntity)

    @Delete
    suspend fun eliminarPerro(perro: PerroEntity)

    @Query("SELECT * FROM perro WHERE id_perro = :id")
    fun getPerro(id: Int): Flow<PerroEntity?>

    @Query("SELECT * FROM perro ORDER BY nombre ASC")
    fun obtenerPerros(): Flow<List<PerroEntity>>
}
