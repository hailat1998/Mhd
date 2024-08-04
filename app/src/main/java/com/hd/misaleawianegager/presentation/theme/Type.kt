package com.hd.misaleawianegager.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


fun selectedTypography(
    selectedFontFamily: FontFamily,
    fontSize: Int,
    letterSpace: Double,
    letterHeight:Int) = Typography(

    bodyLarge = TextStyle(
        fontFamily = selectedFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSize.sp,
        lineHeight = 35.sp,
        letterSpacing = letterSpace.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
     displayLarge = TextStyle(
         fontFamily = FontFamily.Default,
        fontSize = 57.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.25).sp
),
 displayMedium = TextStyle(
     fontFamily = selectedFontFamily,
     fontSize = 45.sp,
     fontWeight = FontWeight.Normal,
     letterSpacing = 0.sp
),
 displaySmall = TextStyle(
     fontFamily = FontFamily.Default,
     fontSize = 36.sp,
     fontWeight = FontWeight.Normal,
     letterSpacing = 0.sp
),
     headlineLarge = TextStyle(
         fontFamily = FontFamily.Default,
         fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
         letterSpacing = 0.sp
),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp
),
 headlineSmall = TextStyle(
     fontFamily = selectedFontFamily,
     fontSize = 24.sp,
     fontWeight = FontWeight.Normal,
     letterSpacing = 0.sp
),


 titleMedium = TextStyle(
     fontFamily = FontFamily.Default,
     fontSize = 16.sp,
     fontWeight = FontWeight.W500,
     letterSpacing = 0.15.sp
),
 titleSmall = TextStyle(
     fontFamily = FontFamily.Default ,
     fontSize = 14.sp,
     fontWeight = FontWeight.W500,
     letterSpacing = 0.1.sp
),
     labelLarge = TextStyle(
         fontFamily = FontFamily.Default ,
         fontSize = 14.sp,
         fontWeight = FontWeight.W500,
         letterSpacing = 0.1.sp
     )
)

