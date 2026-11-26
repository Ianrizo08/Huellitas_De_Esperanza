package com.example.huellitas.ui

import android.database.sqlite.SQLiteConstraintException
import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huellitas.R
import com.example.huellitas.data.AppScreens
import com.example.huellitas.domain.model.RolUsuario
import com.example.huellitas.domain.model.Usuario
import com.example.huellitas.ui.theme.HuellitasTheme

@Composable
fun CuentaPantalla(
    navController: NavController,
    viewModel: UsuarioViewModel = hiltViewModel()
) {
    val usuario by viewModel.usuarioActual.collectAsStateWithLifecycle()
    CuentaContenido(
        navController = navController,
        usuario = usuario,
        onIniciarSesion = { correo, contrasena, resultado ->
            viewModel.iniciarSesion(correo, contrasena, resultado)
        },
        onRegistrar = { nombre, correo, telefono, contrasena, rol, resultado ->
            viewModel.registrar(nombre, correo, telefono, contrasena, rol, resultado)
        },
        onCerrarSesion = { viewModel.cerrarSesion() }
    )
}

@Composable
fun CuentaContenido(
    navController: NavController,
    usuario: Usuario?,
    onIniciarSesion: (String, String, (Usuario?) -> Unit) -> Unit,
    onRegistrar: (String, String, String, String, RolUsuario, (Result<Unit>) -> Unit) -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BarraNavegacion(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            EncabezadoHuellitas(
                nombreUsuario = usuario?.nombre,
                onCuenta = {}
            )
            Spacer(Modifier.height(20.dp))
            if (usuario == null) {
                FormularioAcceso(onIniciarSesion, onRegistrar)
            } else {
                DatosCuenta(
                    usuario = usuario,
                    onAgregarPerro = { navController.navigate(AppScreens.NuevoPerro.route) },
                    onCerrarSesion = onCerrarSesion
                )
            }
        }
    }
}

@Composable
private fun FormularioAcceso(
    onIniciarSesion: (String, String, (Usuario?) -> Unit) -> Unit,
    onRegistrar: (String, String, String, String, RolUsuario, (Result<Unit>) -> Unit) -> Unit
) {
    var registrando by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf(RolUsuario.ADOPTANTE) }

    val errorCampos = stringResource(R.string.cuenta_error_campos)
    val errorCredenciales = stringResource(R.string.cuenta_error_credenciales)
    val errorRegistroCampos = stringResource(R.string.cuenta_error_registro_campos)
    val errorCorreoRepetido = stringResource(R.string.cuenta_error_correo_repetido)
    val errorGenerico = stringResource(R.string.cuenta_error_generico)
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Text(
        text = stringResource(R.string.cuenta_titulo),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Text(
        text = stringResource(R.string.cuenta_subtitulo),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(16.dp))

    FilaOpciones(
        opciones = listOf(false, true),
        seleccionada = registrando,
        texto = { esRegistro ->
            stringResource(
                if (esRegistro) R.string.cuenta_tab_registro else R.string.cuenta_tab_entrar
            )
        },
        onSeleccion = {
            registrando = it
            mensajeError = null
        }
    )
    Spacer(Modifier.height(16.dp))

    if (registrando) {
        CampoTexto(
            valor = nombre,
            onValorCambia = { nombre = it },
            etiqueta = stringResource(R.string.cuenta_nombre),
            maxCaracteres = 100
        )
        Spacer(Modifier.height(12.dp))
    }
    CampoTexto(
        valor = correo,
        onValorCambia = { correo = it },
        etiqueta = stringResource(R.string.cuenta_correo),
        maxCaracteres = 120,
        tipoTeclado = KeyboardType.Email
    )
    Spacer(Modifier.height(12.dp))
    if (registrando) {
        CampoTexto(
            valor = telefono,
            onValorCambia = { nuevo -> if (nuevo.all { it.isDigit() }) telefono = nuevo },
            etiqueta = stringResource(R.string.cuenta_telefono),
            maxCaracteres = 15,
            tipoTeclado = KeyboardType.Phone
        )
        Spacer(Modifier.height(12.dp))
    }
    CampoTexto(
        valor = contrasena,
        onValorCambia = { contrasena = it },
        etiqueta = stringResource(R.string.cuenta_contrasena),
        maxCaracteres = 100,
        tipoTeclado = KeyboardType.Password,
        transformacion = PasswordVisualTransformation()
    )

    if (registrando) {
        Spacer(Modifier.height(16.dp))
        EtiquetaTitulo(stringResource(R.string.cuenta_rol))
        FilaOpciones(
            opciones = listOf(RolUsuario.ADOPTANTE, RolUsuario.VOLUNTARIO),
            seleccionada = rol,
            texto = { stringResource(it.textoRes()) },
            onSeleccion = { rol = it }
        )
    }

    mensajeError?.let {
        Spacer(Modifier.height(12.dp))
        Text(
            text = it,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error
        )
    }

    Spacer(Modifier.height(20.dp))
    Button(
        onClick = {
            mensajeError = null
            if (registrando) {
                val correoValido = Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()
                if (nombre.isBlank() || !correoValido || contrasena.length < 6) {
                    mensajeError = errorRegistroCampos
                } else {
                    onRegistrar(nombre, correo, telefono, contrasena, rol) { resultado ->
                        val fallo = resultado.exceptionOrNull()
                        if (fallo != null) {
                            mensajeError = if (fallo is SQLiteConstraintException) {
                                errorCorreoRepetido
                            } else {
                                errorGenerico
                            }
                        }
                    }
                }
            } else {
                if (correo.isBlank() || contrasena.isBlank()) {
                    mensajeError = errorCampos
                } else {
                    onIniciarSesion(correo, contrasena) { encontrado ->
                        if (encontrado == null) mensajeError = errorCredenciales
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = stringResource(
                if (registrando) R.string.cuenta_boton_registro else R.string.cuenta_boton_entrar
            ),
            style = MaterialTheme.typography.labelLarge
        )
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun DatosCuenta(
    usuario: Usuario,
    onAgregarPerro: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Text(
        text = stringResource(R.string.cuenta_hola, usuario.nombre),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Text(
        text = stringResource(R.string.cuenta_sesion_activa),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(Modifier.height(16.dp))
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = usuario.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.cuenta_dato_correo, usuario.correo),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (usuario.telefono.isNotBlank()) {
                Text(
                    text = stringResource(R.string.cuenta_dato_telefono, usuario.telefono),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = stringResource(
                    R.string.cuenta_dato_rol,
                    stringResource(usuario.rol.textoRes())
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    Spacer(Modifier.height(20.dp))
    Button(
        onClick = onAgregarPerro,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(Icons.Filled.Add, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.catalogo_agregar),
            style = MaterialTheme.typography.labelLarge
        )
    }
    Spacer(Modifier.height(12.dp))
    OutlinedButton(
        onClick = onCerrarSesion,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = stringResource(R.string.cuenta_cerrar_sesion),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CuentaSinSesionPreview() {
    HuellitasTheme {
        CuentaContenido(
            navController = rememberNavController(),
            usuario = null,
            onIniciarSesion = { _, _, _ -> },
            onRegistrar = { _, _, _, _, _, _ -> },
            onCerrarSesion = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CuentaConSesionPreview() {
    HuellitasTheme {
        CuentaContenido(
            navController = rememberNavController(),
            usuario = Usuario(nombre = "Ana López", correo = "ana@correo.com", telefono = "5512345678"),
            onIniciarSesion = { _, _, _ -> },
            onRegistrar = { _, _, _, _, _, _ -> },
            onCerrarSesion = {}
        )
    }
}
