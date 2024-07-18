package com.hd.misaleawianegager.data.repository

import com.hd.misaleawianegager.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingRepository @Inject constructor(private val dataStoreManager: DataStoreManager) {
    val theme: Flow<String?> = dataStoreManager.theme
    val font: Flow<String?> = dataStoreManager.font
    val letterType: Flow<String?> = dataStoreManager.letterType

    suspend fun setTheme(theme: String) {
        dataStoreManager.setTheme(theme)
    }

    suspend fun setFont(font: String) {
        dataStoreManager.setFont(font)
    }

    suspend fun setLetterType(letterType: String) {
        dataStoreManager.setLetterType(letterType)
    }

    suspend fun setLetterSpace(letterSpace: Double ){
        dataStoreManager.setLetterSpace(letterSpace)
    }

    suspend fun setLineHeight(lineHeight: Int){
        dataStoreManager.setLineHeight(lineHeight)
    }


}