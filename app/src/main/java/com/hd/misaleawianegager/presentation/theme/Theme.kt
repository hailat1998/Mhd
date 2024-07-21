package com.hd.misaleawianegager.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
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
    theme: String ,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    selectedFont: Int = R.font.andikaafr_r,
    fontSize: Int = 16,
    letterSpace: Double = 0.5,
    letterHeight:Int = 24 ,
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

   val fontFamily = FontFamily(Font(selectedFont))

    MaterialTheme(
        colorScheme = colorScheme,
        typography = selectedTypography(fontFamily,fontSize, letterSpace, letterHeight ),
        content = content
    )
}