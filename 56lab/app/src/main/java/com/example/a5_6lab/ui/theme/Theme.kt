package com.example.a5_6lab.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    background = Color(0xFF386C94), // Мягкий светло-серый
    primary = Color(0xFF6200EE),    // Насыщенный фиолетовый
    secondary = Color(0xFF03DAC6), // Мягкий бирюзовый
    surface = Color(0xFF386C94),   // Чисто белый
    tertiary = Color(0xFFFFC107),  // Нежный золотистый
    surfaceVariant = Color(0xFFEDEDED), // Бледно-серый
    onSurfaceVariant = Color(0xFF000000), // Средний серый
    tertiaryContainer = Color(0xFFFFE082), // Светлый желтый
    onTertiaryContainer = Color(0xFF212121), // Темный для контраста
    secondaryContainer = Color(0xFFB2DFDB), // Светло-бирюзовый
    inverseSurface = Color(0xFF303030),     // Темно-серый
    error = Color(0xFFB00020)               // Красный с высокой видимостью
)

private val DarkColorScheme = darkColorScheme(
    background = Color(0xFF0E1921), // Глубокий серый
    primary = Color(0xFFBB86FC),    // Нежный фиолетовый
    secondary = Color(0xFF03DAC6), // Мягкий бирюзовый
    surface = Color(0xFF0E1921),   // Чуть светлее фона
    onSurface = Color(0xFFFFFFFF), // Белый для текста
    tertiary = Color(0xFFFFC107),  // Золотистый
    surfaceVariant = Color(0xFF2C2C2C), // Темно-серый
    onSurfaceVariant = Color(0xFFBDBDBD), // Средне-серый
    tertiaryContainer = Color(0xFFFFAB40), // Светло-оранжевый
    onTertiaryContainer = Color(0xFFFF0000), // Темный для контраста
    secondaryContainer = Color(0xFF004D40), // Темный бирюзовый
    inverseSurface = Color(0xFFF5F5F5),     // Светло-серый для обратного
    error = Color(0xFFF60B38)               // Красный с высокой видимостью

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/*@Composable
fun _56LabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}*/

@Composable
fun _56LabTheme(
    darkTheme:
    Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}