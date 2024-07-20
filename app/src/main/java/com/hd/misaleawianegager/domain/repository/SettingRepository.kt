package com.hd.misaleawianegager.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val theme: Flow<String?>
    val font: Flow<Int?>
    val letterSpace: Flow<Double>
    val letterType: Flow<String?>
    val fontSize: Flow<Int>
    val letterHeight: Flow<Int>

    suspend fun setTheme(theme: String)

    suspend fun setFont(font: Int)

    suspend fun setLetterType(letterType: String)

    suspend fun setLetterSpace(letterSpace: Double )

    suspend fun setLineHeight(lineHeight: Int)

    suspend fun setFontSize(fontSize: Int)
}