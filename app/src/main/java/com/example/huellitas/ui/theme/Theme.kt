package com.example.huellitas.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val HuellitasColorScheme = lightColorScheme(
    primary              = Morado,
    onPrimary            = Blanco,
    primaryContainer     = LilaClaro,
    onPrimaryContainer   = GrisOscuro,
    secondary            = Lila,
    onSecondary          = GrisOscuro,
    secondaryContainer   = LilaClaro,
    onSecondaryContainer = Morado,
    tertiary             = Verde,
    onTertiary           = GrisOscuro,
    tertiaryContainer    = VerdeClaro,
    onTertiaryContainer  = TextoSobreVerde,
    background           = Crema,
    onBackground         = GrisOscuro,
    surface              = Blanco,
    onSurface            = GrisOscuro,
    surfaceVariant       = LilaClaro,
    onSurfaceVariant     = Gris,
    outline              = Borde,
    error                = ErrorHuellitas
)

private val HuellitasShapes = Shapes(
    small      = RoundedCornerShape(12.dp),
    medium     = RoundedCornerShape(16.dp),
    large      = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun HuellitasTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colorScheme = HuellitasColorScheme,
        typography = HuellitasTypography,
        shapes = HuellitasShapes,
        content = content
    )
}
