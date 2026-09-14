package com.sabeomnim.app.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val TaegeukRed = Color(0xFFD32F2F)
val TaegeukBlue = Color(0xFF1976D2)
val KukkiwonGold = Color(0xFFFFB300)
val DarkDobok = Color(0xFF121212)
val PureDobokWhite = Color(0xFFFAFAFA)

private val DarkColorScheme = darkColorScheme(
    primary = TaegeukBlue,
    secondary = TaegeukRed,
    tertiary = KukkiwonGold,
    background = DarkDobok,
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0D47A1),
    secondary = Color(0xFFC62828),
    tertiary = Color(0xFFF57F17),
    background = Color(0xFFF8F9FA),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A)
)

@Composable
fun SabeomnimTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
