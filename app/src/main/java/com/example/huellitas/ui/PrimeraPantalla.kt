package com.example.huellitas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huellitas.R
import com.example.huellitas.data.AppScreens
import com.example.huellitas.domain.model.EstadoPerro
import com.example.huellitas.ui.theme.HuellitasTheme

@Composable
fun PrimeraPantalla(
    navController: NavController,
    viewModel: PerroViewModel = hiltViewModel(),
    usuarioViewModel: UsuarioViewModel = hiltViewModel()
) {
    val perros by viewModel.perros.collectAsStateWithLifecycle()
    val usuario by usuarioViewModel.usuarioActual.collectAsStateWithLifecycle()
    PrimeraPantallaContenido(
        navController = navController,
        perrosDisponibles = perros.count { it.estado == EstadoPerro.DISPONIBLE },
        nombreUsuario = usuario?.nombre
    )
}

@Composable
fun PrimeraPantallaContenido(
    navController: NavController,
    perrosDisponibles: Int,
    nombreUsuario: String? = null
) {
    val irAlCatalogo = { navController.navigate(route = AppScreens.SegundaPantalla.route) }
    val irAApoyar = { navController.navigate(route = AppScreens.Apoyar.route) }
    val irACuenta = {
        navController.navigate(route = AppScreens.Cuenta.route) { launchSingleTop = true }
    }

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
            EncabezadoHuellitas(nombreUsuario = nombreUsuario, onCuenta = { irACuenta() })

            Spacer(Modifier.height(20.dp))
            if (nombreUsuario != null) {
                Text(
                    text = stringResource(R.string.cuenta_hola, nombreUsuario.trim().substringBefore(" ")),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = stringResource(R.string.inicio_titulo),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(R.string.inicio_subtitulo),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(20.dp))
            TarjetaPrincipal(onConocer = irAlCatalogo)

            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.inicio_como_ayudar),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))
            OpcionAyuda(
                icono = Icons.Filled.Favorite,
                colorFondoIcono = MaterialTheme.colorScheme.secondary,
                titulo = stringResource(R.string.inicio_adopta_titulo),
                texto = stringResource(R.string.inicio_adopta_texto),
                onClick = irAlCatalogo
            )
            Spacer(Modifier.height(12.dp))
            OpcionAyuda(
                icono = Icons.Filled.Add,
                colorFondoIcono = MaterialTheme.colorScheme.tertiaryContainer,
                titulo = stringResource(R.string.inicio_apoya_titulo),
                texto = stringResource(R.string.inicio_apoya_texto),
                onClick = irAApoyar
            )

            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = stringResource(R.string.inicio_ayuda_titulo),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.inicio_ayuda_cifra, perrosDisponibles),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.inicio_ayuda_texto),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun TarjetaPrincipal(onConocer: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.inicio_hero_linea1),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.inicio_hero_linea2),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.inicio_hero_texto),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = onConocer,
                    modifier = Modifier.height(48.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(R.string.inicio_hero_boton),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            val descripcion = stringResource(R.string.inicio_hero_imagen)
            Text(
                text = stringResource(R.string.emoji_perro_hero),
                fontSize = 48.sp,
                modifier = Modifier.semantics { contentDescription = descripcion }
            )
        }
    }
}

@Composable
fun OpcionAyuda(
    icono: ImageVector,
    colorFondoIcono: Color,
    titulo: String,
    texto: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = colorFondoIcono,
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icono,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = texto,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.ir_a_seccion),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrimeraPantallaPreview() {
    HuellitasTheme {
        PrimeraPantallaContenido(rememberNavController(), perrosDisponibles = 3)
    }
}
