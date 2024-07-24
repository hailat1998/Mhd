package com.hd.misaleawianegager.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
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

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

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

@Composable
fun MisaleawiAnegagerTheme(
    theme: String,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    selectedFont: Int = R.font.andikaafr_r,
    fontSize: State<Int> ,
    letterSpace: State<Double> ,
    letterHeight: State<Int> ,
    content: @Composable () -> Unit
) {
    val colorScheme = when (theme) {
        "light" -> lightColorScheme()
        "dark" -> darkColorScheme()
        else -> if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = theme == "dark"
        }
    }

    var fontFamily = FontFamily(Font(selectedFont))

    var typography = selectedTypography(fontFamily, fontSize.value, letterSpace.value, letterHeight.value)

    LaunchedEffect( selectedFont ,  fontSize.value ,  letterSpace.value , letterHeight.value) {
        fontFamily = FontFamily(Font(selectedFont))
        typography = selectedTypography(fontFamily, fontSize.value, letterSpace.value, letterHeight.value)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = selectedTypography(fontFamily, fontSize.value, letterSpace.value, letterHeight.value),
        content = content
    )
}