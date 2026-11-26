package com.example.huellitas.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.huellitas.domain.model.EstadoPerro
import com.example.huellitas.domain.model.TamanoPerro

@Entity(tableName = "perro")
data class PerroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_perro") val id: Int = 0,
    @ColumnInfo(name = "nombre") val nombre: String = "",
    @ColumnInfo(name = "edad_meses") val edadMeses: Int = 0,
    @ColumnInfo(name = "tamano") val tamano: TamanoPerro = TamanoPerro.MEDIANO,
    @ColumnInfo(name = "personalidad") val personalidad: String = "",
    @ColumnInfo(name = "historia") val historia: String = "",
    @ColumnInfo(name = "cuidados") val cuidados: String = "",
    @ColumnInfo(name = "estado") val estado: EstadoPerro = EstadoPerro.DISPONIBLE,
    @ColumnInfo(name = "fecha_ingreso") val fechaIngreso: Long = 0L
) {
    init {
        require(nombre.length <= 60) { "El nombre no puede tener más de 60 caracteres" }
        require(personalidad.length <= 150) { "La personalidad no puede tener más de 150 caracteres" }
        require(edadMeses >= 0) { "La edad no puede ser negativa" }
    }
}
