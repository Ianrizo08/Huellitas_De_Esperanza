package com.example.huellitas.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.huellitas.R
import com.example.huellitas.domain.model.EstadoPerro
import com.example.huellitas.domain.model.RolUsuario
import com.example.huellitas.domain.model.TamanoPerro
import java.text.DateFormat
import java.util.Date
import java.util.Locale

enum class FiltroPerro(@StringRes val textoRes: Int) {
    TODOS(R.string.filtro_todos),
    CACHORROS(R.string.filtro_cachorros),
    ADULTOS(R.string.filtro_adultos),
    PEQUENOS(R.string.filtro_pequenos)
}

@StringRes
fun EstadoPerro.textoRes(): Int = when (this) {
    EstadoPerro.DISPONIBLE -> R.string.estado_disponible
    EstadoPerro.EN_CUIDADO -> R.string.estado_en_cuidado
    EstadoPerro.ADOPTADO -> R.string.estado_adoptado
}

@StringRes
fun TamanoPerro.textoRes(): Int = when (this) {
    TamanoPerro.PEQUENO -> R.string.tamano_pequeno
    TamanoPerro.MEDIANO -> R.string.tamano_mediano
    TamanoPerro.GRANDE -> R.string.tamano_grande
}

@StringRes
fun RolUsuario.textoRes(): Int = when (this) {
    RolUsuario.ADOPTANTE -> R.string.rol_adoptante
    RolUsuario.VOLUNTARIO -> R.string.rol_voluntario
    RolUsuario.ADMIN -> R.string.rol_admin
}

fun textoFecha(milisegundos: Long): String =
    DateFormat.getDateInstance(DateFormat.LONG, Locale.forLanguageTag("es-MX")).format(Date(milisegundos))

@Composable
fun textoEdad(edadMeses: Int): String {
    val recursos = LocalContext.current.resources
    return if (edadMeses < 12) {
        recursos.getQuantityString(R.plurals.edad_meses, edadMeses, edadMeses)
    } else {
        val anios = edadMeses / 12
        recursos.getQuantityString(R.plurals.edad_anios, anios, anios)
    }
}
