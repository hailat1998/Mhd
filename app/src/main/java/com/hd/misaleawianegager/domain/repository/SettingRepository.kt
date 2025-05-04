package com.hd.misaleawianegager.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val theme: Flow<String?>
    val font: Flow<String?>
    val letterSpace: Flow<Double>
    val letterType: Flow<String?>
    val fontSize: Flow<Int>
    val letterHeight: Flow<Int>
    val onBoardingShown: Flow<Boolean>

    suspend fun setTheme(theme: String)

    suspend fun setFont(font: String)

    suspend fun setLetterType(letterType: String)

    suspend fun setLetterSpace(letterSpace: Double )

    suspend fun setLineHeight(lineHeight: Int)

    suspend fun setFontSize(fontSize: Int)

    suspend fun setOnBoarding(isShown: Boolean)
}