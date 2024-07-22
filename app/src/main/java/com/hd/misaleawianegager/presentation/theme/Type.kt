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
    fontSize: Int, letterSpace: Double,
    letterHeight:Int) = Typography(
//
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
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
//    labelMedium = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )


)

enum class Font(res: Int){
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