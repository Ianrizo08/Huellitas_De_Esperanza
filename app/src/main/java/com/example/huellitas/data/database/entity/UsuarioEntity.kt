package com.example.huellitas.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.huellitas.domain.model.RolUsuario

@Entity(
    tableName = "usuario",
    indices = [Index(value = ["correo"], unique = true)]
)
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_usuario") val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "correo") val correo: String = "",
    @ColumnInfo(name = "telefono") val telefono: String = "",
    @ColumnInfo(name = "contrasena_hash") val contrasenaHash: String = "",
    @ColumnInfo(name = "rol") val rol: RolUsuario = RolUsuario.ADOPTANTE,
    @ColumnInfo(name = "fecha_registro") val fechaRegistro: Long = 0L
) {
    init {
        require(nombre.length <= 100) { "El nombre no puede tener más de 100 caracteres" }
        require(correo.length <= 120) { "El correo no puede tener más de 120 caracteres" }
        require(telefono.length <= 15) { "El teléfono no puede tener más de 15 caracteres" }
        require(contrasenaHash.length <= 255) { "El hash no puede tener más de 255 caracteres" }
    }
}
