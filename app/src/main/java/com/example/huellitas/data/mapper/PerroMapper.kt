package com.example.huellitas.data.mapper

import com.example.huellitas.data.database.entity.PerroEntity
import com.example.huellitas.domain.model.Perro

fun PerroEntity.toDomain(): Perro = Perro(
    id = id,
    nombre = nombre,
    edadMeses = edadMeses,
    tamano = tamano,
    personalidad = personalidad,
    historia = historia,
    cuidados = cuidados,
    estado = estado,
    fechaIngreso = fechaIngreso
)

fun Perro.toEntity(): PerroEntity = PerroEntity(
    id = id,
    nombre = nombre,
    edadMeses = edadMeses,
    tamano = tamano,
    personalidad = personalidad,
    historia = historia,
    cuidados = cuidados,
    estado = estado,
    fechaIngreso = fechaIngreso
)
