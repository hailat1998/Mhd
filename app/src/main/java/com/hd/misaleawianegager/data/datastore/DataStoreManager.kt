package com.hd.misaleawianegager.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hd.misaleawianegager.domain.datastoremanager.DataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class DataStoreManagerImpl @Inject constructor(@ApplicationContext private val context: Context):
    DataStoreManager {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val FONT_KEY = stringPreferencesKey("font")
        val LETTER_TYPE_KEY = stringPreferencesKey("letterType")
        val LETTER_SPACE_KEY = doublePreferencesKey("letterSpace")
        val FONT_SIZE_KEY = intPreferencesKey("font size")
        val LINE_HEIGHT_KEY = intPreferencesKey("line height")
        val ONBOARD_SHOWN = booleanPreferencesKey("onBoard")
        val SHOW_BOTTOM_BAR_ON_DETAILS = booleanPreferencesKey("show btm bar detail")
    }

    override val theme: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: "system"
        }

    override val font: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[FONT_KEY] ?: "Default"
        }

    override val letterType: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LETTER_TYPE_KEY] ?: "01Ha.txt"
        }

    override val letterSpace: Flow<Double> = context.dataStore.data
        .map{ preferences ->
           preferences[LETTER_SPACE_KEY] ?: 0.5
        }

    override val fontSize: Flow<Int> = context.dataStore.data
        .map{ preferences ->

            preferences[FONT_SIZE_KEY] ?: 16

        }


    override val lineHeight: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[LINE_HEIGHT_KEY] ?: 24
        }

    override val onBoardShown: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARD_SHOWN] ?: false
        }
    override val showBottomBarOnDetails: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SHOW_BOTTOM_BAR_ON_DETAILS] ?: true
        }


    override suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    override suspend fun setFont(font: String) {
        context.dataStore.edit { preferences ->
            preferences[FONT_KEY] = font
        }
    }

    override suspend fun setLetterType(letterType: String) {
        context.dataStore.edit { preferences ->
            preferences[LETTER_TYPE_KEY] = letterType
        }
    }

    override suspend fun setLetterSpace(letterSpace: Double ){
        context.dataStore.edit { preferences ->
            preferences[LETTER_SPACE_KEY] =  letterSpace
        }
    }

    override suspend fun setLineHeight(lineHeight: Int){
        context.dataStore.edit { preferences ->
            preferences[LINE_HEIGHT_KEY] =  lineHeight
        }
    }

    override suspend fun setOnBoarding(isShown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARD_SHOWN] =  isShown
        }
    }

    override suspend fun setShowBottomBarOnDetails(isShown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_BOTTOM_BAR_ON_DETAILS] =  isShown
        }
    }

    override suspend fun setFontSize(fontSize: Int){
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_KEY] =  fontSize
        }
    }
}