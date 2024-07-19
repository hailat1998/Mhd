package com.hd.misaleawianegager.data.repository

import com.hd.misaleawianegager.data.datastore.DataStoreManager
import com.hd.misaleawianegager.domain.repository.SettingRepository
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingRepositoryImpl @Inject constructor(private val dataStoreManager: DataStoreManager) : SettingRepository{
    override val theme: Flow<String?> = dataStoreManager.theme
    override val font: Flow<String?> = dataStoreManager.font
    override val letterType: Flow<String?> = dataStoreManager.letterType
    override val letterSpace: Flow<Double> = dataStoreManager.letterSpace
    override val fontSize: Flow<Int> = dataStoreManager.fontSize
    override val letterHeight: Flow<Int> = dataStoreManager.lineHeight

    override suspend fun setTheme(theme: String) {
        dataStoreManager.setTheme(theme)
    }

    override suspend fun setFont(font: String) {
        dataStoreManager.setFont(font)
    }

    override suspend fun setLetterType(letterType: String) {
        dataStoreManager.setLetterType(letterType)
    }

    override suspend fun setLetterSpace(letterSpace: Double ){
        dataStoreManager.setLetterSpace(letterSpace)
    }

    override suspend fun setLineHeight(lineHeight: Int){
        dataStoreManager.setLineHeight(lineHeight)
    }

  override suspend fun setFontSize(fontSize: Int){
      dataStoreManager.setFontSize(fontSize)
  }
}