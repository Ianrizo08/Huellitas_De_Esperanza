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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.huellitas.R
import com.example.huellitas.domain.model.EstadoPerro
import com.example.huellitas.domain.model.Perro
import com.example.huellitas.domain.model.TamanoPerro
import com.example.huellitas.ui.theme.HuellitasTheme

@Composable
fun PerfilPerroPantalla(
    navController: NavController,
    idPerro: Int,
    viewModel: PerroViewModel = hiltViewModel()
) {
    val perros by viewModel.perros.collectAsStateWithLifecycle()
    PerfilPerroContenido(
        perro = perros.firstOrNull { it.id == idPerro },
        cargando = perros.isEmpty(),
        onVolver = { navController.popBackStack() }
    )
}

@Composable
fun PerfilPerroContenido(perro: Perro?, cargando: Boolean, onVolver: () -> Unit) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            BarraSuperior(
                titulo = perro?.nombre ?: stringResource(R.string.logo_nombre),
                onVolver = onVolver
            )
            Spacer(Modifier.height(12.dp))
            when {
                perro != null -> DetallePerro(perro)
                cargando -> Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                else -> Text(
                    text = stringResource(R.string.perfil_no_encontrado),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun DetallePerro(perro: Perro) {
    val descripcionFoto = stringResource(R.string.catalogo_foto, perro.nombre)
    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .semantics { contentDescription = descripcionFoto }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.emoji_perro), fontSize = 88.sp)
        }
    }

    Spacer(Modifier.height(16.dp))
    EtiquetaEstado(perro.estado)
    Spacer(Modifier.height(8.dp))
    Text(
        text = perro.nombre,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(Modifier.height(16.dp))
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        DatoPerro(
            titulo = stringResource(R.string.perfil_edad),
            valor = textoEdad(perro.edadMeses),
            modifier = Modifier.weight(1f)
        )
        DatoPerro(
            titulo = stringResource(R.string.perfil_tamano),
            valor = stringResource(perro.tamano.textoRes()),
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(Modifier.height(12.dp))
    SeccionPerro(
        titulo = stringResource(R.string.perfil_personalidad),
        texto = perro.personalidad
    )
    Spacer(Modifier.height(12.dp))
    SeccionPerro(
        titulo = stringResource(R.string.perfil_historia),
        texto = perro.historia
    )
    Spacer(Modifier.height(12.dp))
    SeccionPerro(
        titulo = stringResource(R.string.perfil_cuidados),
        texto = perro.cuidados
    )
    Spacer(Modifier.height(12.dp))
    SeccionPerro(
        titulo = stringResource(R.string.perfil_ingreso),
        texto = textoFecha(perro.fechaIngreso)
    )
    Spacer(Modifier.height(16.dp))
}

@Composable
private fun DatoPerro(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun SeccionPerro(titulo: String, texto: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = texto.ifBlank { stringResource(R.string.perfil_sin_datos) },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilPerroPreview() {
    HuellitasTheme {
        PerfilPerroContenido(
            perro = Perro(
                id = 1,
                nombre = "Luna",
                edadMeses = 24,
                tamano = TamanoPerro.MEDIANO,
                personalidad = "Cariñosa y tranquila",
                historia = "Rescatada de la calle, ya convive con otros perros.",
                cuidados = "Paseo diario y vacunas al corriente.",
                estado = EstadoPerro.DISPONIBLE
            ),
            cargando = false,
            onVolver = {}
        )
    }
}
