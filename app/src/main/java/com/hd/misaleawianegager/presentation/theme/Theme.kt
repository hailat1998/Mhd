package com.hd.misaleawianegager.presentation.theme

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.core.view.WindowCompat
import com.hd.misaleawianegager.R


private val DarkColorPalette = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = DarkOnPrimary,
    onSecondary = DarkOnSecondary,
    onBackground = DarkOnBackground,
    onSurface = DarkOnSurface,
    primaryContainer = DarkPrimaryContainer,
    surfaceContainer = DarkPrimaryVariant,
    surfaceContainerLow = DarkPrimaryVariantBackground
)
private val LightColorPalette = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground,
    surface = LightSurface,
    onPrimary = LightOnPrimary,
    onSecondary = LightOnSecondary,
    onBackground = LightOnBackground,
    onSurface = LightOnSurface,
    primaryContainer = LightPrimaryContainer,
    surfaceContainer = LightPrimaryVariant,
            surfaceContainerLow = LightPrimaryVariantBackground
)

@Composable
fun MisaleawiAnegagerTheme(
    theme: String,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    selectedFont: State<String?> ,
    fontSize: State<Int> ,
    letterSpace: State<Double> ,
    letterHeight: State<Int> ,
    content: @Composable () -> Unit
) {
    val colorScheme = when (theme) {
        "light" -> LightColorPalette
        "dark" -> DarkColorPalette
        else -> if(isSystemInDarkTheme()) DarkColorPalette else LightColorPalette
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primaryContainer.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = theme == "dark"
        }
    }

    val fontFamily = when(selectedFont.value){
        "abyssinica_gentium" -> FontFamily(Font(R.font.abyssinica_gentium))
        "andikaafr_r" -> FontFamily(Font(R.font.andikaafr_r))
        "charterbr_roman" -> FontFamily(Font(R.font.charterbr_roman))
        "desta_gentium" -> FontFamily(Font(R.font.desta_gentium))
        "gfzemen_regular" -> FontFamily(Font(R.font.gfzemen_regular))
        "jiret" -> FontFamily(Font(R.font.jiret))
        "nyala" -> FontFamily(Font(R.font.nyala))
        "washrasb" -> FontFamily(Font(R.font.washrasb))
        "wookianos" -> FontFamily(Font(R.font.wookianos))
        "yebse" -> FontFamily(Font(R.font.yebse))
        "serif" -> FontFamily.Serif
        else -> FontFamily.Default
    }




    MaterialTheme(
        colorScheme = colorScheme,
        typography = selectedTypography(fontFamily, fontSize.value, letterSpace.value, letterHeight.value),
        content = content
    )
}