package com.example.huellitas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.huellitas.R
import com.example.huellitas.data.AppScreens
import com.example.huellitas.domain.model.EstadoPerro
import com.example.huellitas.domain.model.Perro
import com.example.huellitas.domain.model.TamanoPerro
import com.example.huellitas.ui.theme.HuellitasTheme

@Composable
fun SegundaPantalla(
    navController: NavController,
    viewModel: PerroViewModel = hiltViewModel(),
    usuarioViewModel: UsuarioViewModel = hiltViewModel()
) {
    val perros by viewModel.perros.collectAsStateWithLifecycle()
    val usuario by usuarioViewModel.usuarioActual.collectAsStateWithLifecycle()
    SegundaPantallaContenido(navController, perros, usuario?.nombre)
}

@Composable
fun SegundaPantallaContenido(
    navController: NavController,
    todosLosPerros: List<Perro>,
    nombreUsuario: String? = null
) {
    var busqueda by remember { mutableStateOf("") }
    var filtro by remember { mutableStateOf(FiltroPerro.TODOS) }

    val perros = todosLosPerros.filter { perro ->
        val noAdoptado = perro.estado != EstadoPerro.ADOPTADO
        val texto = busqueda.trim()
        val coincideNombre = perro.nombre.contains(texto, ignoreCase = true) ||
            perro.personalidad.contains(texto, ignoreCase = true)
        val coincideFiltro = when (filtro) {
            FiltroPerro.TODOS -> true
            FiltroPerro.CACHORROS -> perro.esCachorro
            FiltroPerro.ADULTOS -> !perro.esCachorro
            FiltroPerro.PEQUENOS -> perro.esPequeno
        }
        noAdoptado && coincideNombre && coincideFiltro
    }

    val fondosFoto = listOf(
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.primaryContainer
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BarraNavegacion(navController) }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    EncabezadoHuellitas(
                        nombreUsuario = nombreUsuario,
                        onCuenta = {
                            navController.navigate(AppScreens.Cuenta.route) { launchSingleTop = true }
                        }
                    )
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.catalogo_titulo),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = stringResource(R.string.catalogo_subtitulo),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate(AppScreens.NuevoPerro.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
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
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { busqueda = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(stringResource(R.string.catalogo_buscar)) },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        trailingIcon = {
                            if (busqueda.isNotEmpty()) {
                                IconButton(onClick = { busqueda = "" }) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = stringResource(R.string.catalogo_limpiar)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(FiltroPerro.entries) { opcion ->
                            FilterChip(
                                selected = filtro == opcion,
                                onClick = { filtro = opcion },
                                label = {
                                    Text(
                                        text = stringResource(opcion.textoRes),
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                shape = MaterialTheme.shapes.large,
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    labelColor = MaterialTheme.colorScheme.primary,
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                border = null
                            )
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }

            if (perros.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = stringResource(R.string.catalogo_vacio),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

            itemsIndexed(perros, key = { _, perro -> perro.id }) { indice, perro ->
                TarjetaPerro(
                    perro = perro,
                    colorFoto = fondosFoto[indice % fondosFoto.size],
                    onVerPerfil = {
                        navController.navigate(AppScreens.PerfilPerro.crearRuta(perro.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun TarjetaPerro(perro: Perro, colorFoto: Color, onVerPerfil: () -> Unit) {
    val nombre = perro.nombre
    val descripcionFoto = stringResource(R.string.catalogo_foto, nombre)

    Card(
        onClick = onVerPerfil,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(12.dp)) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = colorFoto,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .semantics { contentDescription = descripcionFoto }
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.emoji_perro), fontSize = 40.sp)
                }
            }

            Spacer(Modifier.height(10.dp))
            EtiquetaEstado(perro.estado)

            Spacer(Modifier.height(8.dp))
            Text(
                text = nombre,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(
                    R.string.catalogo_datos,
                    textoEdad(perro.edadMeses),
                    stringResource(perro.tamano.textoRes())
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(
                onClick = onVerPerfil,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(R.string.catalogo_ver_perfil),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
fun EtiquetaEstado(estado: EstadoPerro) {
    val (fondo, texto) = when (estado) {
        EstadoPerro.DISPONIBLE ->
            MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        EstadoPerro.EN_CUIDADO ->
            MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        EstadoPerro.ADOPTADO ->
            MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Surface(shape = MaterialTheme.shapes.large, color = fondo, contentColor = texto) {
        Text(
            text = stringResource(estado.textoRes()),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SegundaPantallaPreview() {
    val ejemplo = listOf(
        Perro(id = 1, nombre = "Luna", edadMeses = 24, tamano = TamanoPerro.MEDIANO),
        Perro(id = 2, nombre = "Max", edadMeses = 8, tamano = TamanoPerro.PEQUENO),
        Perro(id = 3, nombre = "Milo", edadMeses = 48, tamano = TamanoPerro.GRANDE, estado = EstadoPerro.EN_CUIDADO)
    )
    HuellitasTheme {
        SegundaPantallaContenido(rememberNavController(), ejemplo)
    }
}
