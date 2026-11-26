package com.example.huellitas.data.mapper

import com.example.huellitas.data.database.entity.UsuarioEntity
import com.example.huellitas.domain.model.Usuario

fun UsuarioEntity.toDomain(): Usuario = Usuario(
    id = id,
    nombre = nombre,
    correo = correo,
    telefono = telefono,
    contrasenaHash = contrasenaHash,
    rol = rol,
    fechaRegistro = fechaRegistro
)

fun Usuario.toEntity(): UsuarioEntity = UsuarioEntity(
    id = id,
    nombre = nombre,
    correo = correo,
    telefono = telefono,
    contrasenaHash = contrasenaHash,
    rol = rol,
    fechaRegistro = fechaRegistro
)
