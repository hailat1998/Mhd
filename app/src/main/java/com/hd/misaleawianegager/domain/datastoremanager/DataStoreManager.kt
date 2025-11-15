package com.hd.misaleawianegager.domain.datastoremanager

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {

    val theme: Flow<String>
    val font: Flow<String>
    val letterType: Flow<String>
    val letterSpace: Flow<Double>
    val fontSize: Flow<Int>
    val lineHeight: Flow<Int>
    val onBoardShown: Flow<Boolean>
    val showBottomBarOnDetails: Flow<Boolean>

    suspend fun setTheme(theme: String)
    suspend fun setFont(font: String)
    suspend fun setLetterType(letterType: String)
    suspend fun setLetterSpace(letterSpace: Double)
    suspend fun setFontSize(fontSize: Int)
    suspend fun setLineHeight(lineHeight: Int)
    suspend fun setOnBoarding(isShown: Boolean)
    suspend fun setShowBottomBarOnDetails(isShown: Boolean)
}
