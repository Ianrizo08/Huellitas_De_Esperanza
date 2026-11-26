package com.example.huellitas.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.huellitas.data.database.dao.PerroDao
import com.example.huellitas.data.database.dao.UsuarioDao
import com.example.huellitas.data.database.entity.PerroEntity
import com.example.huellitas.data.database.entity.UsuarioEntity

@Database(
    entities = [UsuarioEntity::class, PerroEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun perroDao(): PerroDao
}
