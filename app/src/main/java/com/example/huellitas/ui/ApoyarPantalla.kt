package com.example.huellitas.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huellitas.R
import com.example.huellitas.data.AppScreens
import com.example.huellitas.ui.theme.HuellitasTheme

private enum class OpcionApoyo(
    @StringRes val tituloRes: Int,
    @StringRes val textoRes: Int,
    @StringRes val detalleRes: Int,
    val icono: ImageVector,
    val llevaACuenta: Boolean
) {
    DONACIONES(
        R.string.apoyar_donar_titulo,
        R.string.apoyar_donar_texto,
        R.string.apoyar_donar_detalle,
        Icons.Filled.Favorite,
        false
    ),
    ALIMENTO(
        R.string.apoyar_alimento_titulo,
        R.string.apoyar_alimento_texto,
        R.string.apoyar_alimento_detalle,
        Icons.Filled.ShoppingCart,
        false
    ),
    VOLUNTARIADO(
        R.string.apoyar_voluntariado_titulo,
        R.string.apoyar_voluntariado_texto,
        R.string.apoyar_voluntariado_detalle,
        Icons.Filled.Person,
        true
    )
}

@Composable
fun ApoyarPantalla(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel = hiltViewModel()
) {
    val usuario by usuarioViewModel.usuarioActual.collectAsStateWithLifecycle()
    ApoyarContenido(navController, usuario?.nombre)
}

@Composable
fun ApoyarContenido(navController: NavController, nombreUsuario: String? = null) {
    var seleccionada by remember { mutableStateOf<OpcionApoyo?>(null) }
    val colores = listOf(
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.primaryContainer
    )

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
                nombreUsuario = nombreUsuario,
                onCuenta = {
                    navController.navigate(AppScreens.Cuenta.route) { launchSingleTop = true }
                }
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.apoyar_titulo),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.apoyar_subtitulo),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))
            OpcionApoyo.entries.forEachIndexed { indice, opcion ->
                OpcionAyuda(
                    icono = opcion.icono,
                    colorFondoIcono = colores[indice % colores.size],
                    titulo = stringResource(opcion.tituloRes),
                    texto = stringResource(opcion.textoRes),
                    onClick = { seleccionada = opcion }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }

    seleccionada?.let { opcion ->
        AlertDialog(
            onDismissRequest = { seleccionada = null },
            title = { Text(stringResource(opcion.tituloRes)) },
            text = { Text(stringResource(opcion.detalleRes)) },
            confirmButton = {
                if (opcion.llevaACuenta) {
                    TextButton(onClick = {
                        seleccionada = null
                        navController.navigate(AppScreens.Cuenta.route) { launchSingleTop = true }
                    }) {
                        Text(stringResource(R.string.apoyar_ir_cuenta))
                    }
                } else {
                    TextButton(onClick = { seleccionada = null }) {
                        Text(stringResource(R.string.apoyar_cerrar))
                    }
                }
            },
            dismissButton = {
                if (opcion.llevaACuenta) {
                    TextButton(onClick = { seleccionada = null }) {
                        Text(stringResource(R.string.apoyar_cerrar))
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ApoyarPantallaPreview() {
    HuellitasTheme {
        ApoyarContenido(rememberNavController())
    }
}
