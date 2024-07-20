package com.hd.misaleawianegager.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class DataStoreManager @Inject constructor( @ApplicationContext private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val FONT_KEY = intPreferencesKey("font")
        val LETTER_TYPE_KEY = stringPreferencesKey("letterType")
        val LETTER_SPACE_KEY = doublePreferencesKey("letterSpace")
        val FONT_SIZE_KEY = intPreferencesKey("font size")
        val LINE_HEIGHT_KEY = intPreferencesKey("line height")
    }

    val theme: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: "system"
        }

    val font: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[FONT_KEY] ?: R.font.abyssinica_gentium
        }

    val letterType: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LETTER_TYPE_KEY] ?: "01Ha.txt"
        }

    val letterSpace: Flow<Double> = context.dataStore.data
        .map{ preferences ->
           preferences[LETTER_SPACE_KEY] ?: 0.5
        }

    val fontSize: Flow<Int> = context.dataStore.data
        .map{ preferences ->
            preferences[FONT_SIZE_KEY] ?: 16
        }


    val lineHeight: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[LINE_HEIGHT_KEY] ?: 24
        }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    suspend fun setFont(font: Int) {
        context.dataStore.edit { preferences ->
            preferences[FONT_KEY] = font
        }
    }

    suspend fun setLetterType(letterType: String) {
        context.dataStore.edit { preferences ->
            preferences[LETTER_TYPE_KEY] = letterType
        }
    }

    suspend fun setLetterSpace(letterSpace: Double ){
        context.dataStore.edit { preferences ->
            preferences[LETTER_SPACE_KEY] =  letterSpace
        }
    }


    suspend fun setLineHeight(lineHeight: Int){
        context.dataStore.edit { preferences ->
            preferences[LINE_HEIGHT_KEY] =  lineHeight
        }
    }

    suspend fun setFontSize(fontSize: Int){
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_KEY] =  fontSize
        }
    }
}