package com.gym.delta.ui.theme

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

private val DarkColorScheme = darkColorScheme(
    background = GreyBase,

    primary = Yellow,
    onPrimaryContainer = DarkYellow,
    primaryContainer = Navy,

//    secondary = DarkYellow,
    onSecondaryContainer = DarkYellow,
    secondaryContainer = DarkNavy,

//    surface = GreyMid,
//    onSurface = OffWhite,
    tertiaryContainer = Navy,
//    onTertiaryContainer = DarkNavy,
)

private val LightColorScheme = lightColorScheme(
    //Other default colors to override
    background = Color.White
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),

)

@Composable
fun DeltaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = kanitTypography,
        content = content
    )
}