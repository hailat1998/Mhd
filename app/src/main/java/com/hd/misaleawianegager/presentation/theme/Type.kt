package com.hd.misaleawianegager.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hd.misaleawianegager.R

// Set of Material typography styles to start with
fun selectedTypography(
    selectedFontFamily: FontFamily,
    fontSize: Int,
    letterSpace: Double,
    letterHeight:Int) = Typography(

    bodyLarge = TextStyle(
        fontFamily = selectedFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = fontSize.sp,
        lineHeight = letterHeight.sp,
        letterSpacing = letterSpace.sp
    )
    ,
    titleLarge = TextStyle(
        fontFamily = selectedFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = selectedFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = selectedFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
     displayLarge = TextStyle(
         fontFamily = selectedFontFamily,
        fontSize = 57.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = (-0.25).sp
)
,
 displayMedium = TextStyle(
     fontFamily = selectedFontFamily,
     fontSize = 45.sp,
     fontWeight = FontWeight.Normal,
     letterSpacing = 0.sp
)
,
 displaySmall = TextStyle(
     fontFamily = selectedFontFamily,
     fontSize = 36.sp,
     fontWeight = FontWeight.Normal,
     letterSpacing = 0.sp
),
     headlineLarge = TextStyle(
         fontFamily = selectedFontFamily,
         fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
         letterSpacing = 0.sp
)
             ,
    headlineMedium = TextStyle(
        fontFamily = selectedFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp
)
,
 headlineSmall = TextStyle(
     fontFamily = selectedFontFamily,
     fontSize = 24.sp,
     fontWeight = FontWeight.Normal,
     letterSpacing = 0.sp
),


 titleMedium = TextStyle(
     fontFamily = selectedFontFamily,
     fontSize = 16.sp,
     fontWeight = FontWeight.W500,
     letterSpacing = 0.15.sp
)
,
 titleSmall = TextStyle(
     fontFamily = selectedFontFamily ,
     fontSize = 14.sp,
     fontWeight = FontWeight.W500,
     letterSpacing = 0.1.sp
),
     labelLarge = TextStyle(
         fontFamily = selectedFontFamily ,
         fontSize = 14.sp,
         fontWeight = FontWeight.W500,
         letterSpacing = 0.1.sp
     )

)

enum class Font( res: Int){
    abyssinica_gentium(R.font.abyssinica_gentium),
    andikaafr_r(R.font.andikaafr_r),
    charterbr_roman(R.font.charterbr_roman),
    desta_gentium(R.font.desta_gentium),
    gfzemen(R.font.gfzemen_regular),
    jiret(R.font.jiret),
    nyala(R.font.nyala),
    washrasb(R.font.washrasb),
    wookianos(R.font.wookianos),
    yebse(R.font.yebse)
}