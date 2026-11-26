package com.example.huellitas.domain.model

enum class TamanoPerro { PEQUENO, MEDIANO, GRANDE }

enum class EstadoPerro { DISPONIBLE, EN_CUIDADO, ADOPTADO }

data class Perro(
    val id: Int = 0,
    val nombre: String = "",
    val edadMeses: Int = 0,
    val tamano: TamanoPerro = TamanoPerro.MEDIANO,
    val personalidad: String = "",
    val historia: String = "",
    val cuidados: String = "",
    val estado: EstadoPerro = EstadoPerro.DISPONIBLE,
    val fechaIngreso: Long = System.currentTimeMillis()
) {
    val esCachorro: Boolean get() = edadMeses < 12
    val esPequeno: Boolean get() = tamano == TamanoPerro.PEQUENO
}
