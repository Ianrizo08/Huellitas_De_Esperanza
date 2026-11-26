package com.example.huellitas.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.huellitas.R
import com.example.huellitas.data.AppScreens
import com.example.huellitas.domain.model.EstadoPerro
import com.example.huellitas.domain.model.Perro
import com.example.huellitas.domain.model.TamanoPerro
import com.example.huellitas.ui.theme.HuellitasTheme

private enum class UnidadEdad { MESES, ANIOS }

@Composable
fun NuevoPerroPantalla(
    navController: NavController,
    viewModel: PerroViewModel = hiltViewModel(),
    usuarioViewModel: UsuarioViewModel = hiltViewModel()
) {
    val usuario by usuarioViewModel.usuarioActual.collectAsStateWithLifecycle()
    val contexto = LocalContext.current
    NuevoPerroContenido(
        sesionIniciada = usuario != null,
        onVolver = { navController.popBackStack() },
        onIrACuenta = {
            navController.navigate(AppScreens.Cuenta.route) { launchSingleTop = true }
        },
        onGuardar = { perro ->
            viewModel.agregarPerro(perro)
            Toast.makeText(
                contexto,
                contexto.getString(R.string.nuevo_guardado, perro.nombre),
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }
    )
}

@Composable
fun NuevoPerroContenido(
    sesionIniciada: Boolean,
    onVolver: () -> Unit,
    onIrACuenta: () -> Unit,
    onGuardar: (Perro) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var unidad by remember { mutableStateOf(UnidadEdad.ANIOS) }
    var tamano by remember { mutableStateOf(TamanoPerro.MEDIANO) }
    var estado by remember { mutableStateOf(EstadoPerro.DISPONIBLE) }
    var personalidad by remember { mutableStateOf("") }
    var historia by remember { mutableStateOf("") }
    var cuidados by remember { mutableStateOf("") }
    var errorNombre by remember { mutableStateOf(false) }
    var errorEdad by remember { mutableStateOf(false) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            BarraSuperior(
                titulo = stringResource(R.string.nuevo_titulo),
                onVolver = onVolver
            )
            Spacer(Modifier.height(12.dp))

            if (!sesionIniciada) {
                Text(
                    text = stringResource(R.string.nuevo_requiere_sesion),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onIrACuenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.nuevo_ir_cuenta),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.nuevo_subtitulo),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))

                CampoTexto(
                    valor = nombre,
                    onValorCambia = {
                        nombre = it
                        errorNombre = false
                    },
                    etiqueta = stringResource(R.string.nuevo_nombre),
                    maxCaracteres = 60,
                    error = errorNombre
                )
                if (errorNombre) {
                    MensajeError(stringResource(R.string.nuevo_error_nombre))
                }

                Spacer(Modifier.height(12.dp))
                CampoTexto(
                    valor = edad,
                    onValorCambia = { nuevo ->
                        if (nuevo.all { it.isDigit() }) {
                            edad = nuevo
                            errorEdad = false
                        }
                    },
                    etiqueta = stringResource(R.string.nuevo_edad),
                    maxCaracteres = 3,
                    tipoTeclado = KeyboardType.Number,
                    error = errorEdad
                )
                if (errorEdad) {
                    MensajeError(stringResource(R.string.nuevo_error_edad))
                }
                Spacer(Modifier.height(8.dp))
                FilaOpciones(
                    opciones = UnidadEdad.entries,
                    seleccionada = unidad,
                    texto = {
                        stringResource(
                            if (it == UnidadEdad.MESES) R.string.nuevo_unidad_meses
                            else R.string.nuevo_unidad_anios
                        )
                    },
                    onSeleccion = { unidad = it }
                )

                Spacer(Modifier.height(16.dp))
                EtiquetaTitulo(stringResource(R.string.nuevo_tamano))
                FilaOpciones(
                    opciones = TamanoPerro.entries,
                    seleccionada = tamano,
                    texto = { stringResource(it.textoRes()) },
                    onSeleccion = { tamano = it }
                )

                Spacer(Modifier.height(16.dp))
                EtiquetaTitulo(stringResource(R.string.nuevo_estado))
                FilaOpciones(
                    opciones = listOf(EstadoPerro.DISPONIBLE, EstadoPerro.EN_CUIDADO),
                    seleccionada = estado,
                    texto = { stringResource(it.textoRes()) },
                    onSeleccion = { estado = it }
                )

                Spacer(Modifier.height(16.dp))
                CampoTexto(
                    valor = personalidad,
                    onValorCambia = { personalidad = it },
                    etiqueta = stringResource(R.string.nuevo_personalidad),
                    maxCaracteres = 150
                )
                Spacer(Modifier.height(12.dp))
                CampoTexto(
                    valor = historia,
                    onValorCambia = { historia = it },
                    etiqueta = stringResource(R.string.nuevo_historia),
                    unaLinea = false,
                    minLineas = 3
                )
                Spacer(Modifier.height(12.dp))
                CampoTexto(
                    valor = cuidados,
                    onValorCambia = { cuidados = it },
                    etiqueta = stringResource(R.string.nuevo_cuidados),
                    unaLinea = false,
                    minLineas = 3
                )

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        errorNombre = nombre.isBlank()
                        errorEdad = edad.isBlank()
                        if (!errorNombre && !errorEdad) {
                            val valorEdad = edad.toInt()
                            val edadMeses = if (unidad == UnidadEdad.ANIOS) valorEdad * 12 else valorEdad
                            onGuardar(
                                Perro(
                                    nombre = nombre.trim(),
                                    edadMeses = edadMeses,
                                    tamano = tamano,
                                    personalidad = personalidad.trim(),
                                    historia = historia.trim(),
                                    cuidados = cuidados.trim(),
                                    estado = estado,
                                    fechaIngreso = System.currentTimeMillis()
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.nuevo_guardar),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun MensajeError(texto: String) {
    Text(
        text = texto,
        modifier = Modifier.padding(start = 16.dp, top = 4.dp),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NuevoPerroPreview() {
    HuellitasTheme {
        NuevoPerroContenido(
            sesionIniciada = true,
            onVolver = {},
            onIrACuenta = {},
            onGuardar = {}
        )
    }
}
