package com.hd.misaleawianegager.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
        val FONT_KEY = stringPreferencesKey("font")
        val LETTER_TYPE_KEY = stringPreferencesKey("letterType")
    }

    val theme: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: "light"
        }

    val font: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[FONT_KEY] ?: "01Ha.txt"
        }

    val letterType: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LETTER_TYPE_KEY] ?: "01Ha.txt"
        }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    suspend fun setFont(font: String) {
        context.dataStore.edit { preferences ->
            preferences[FONT_KEY] = font
        }
    }

    suspend fun setLetterType(letterType: String) {
        context.dataStore.edit { preferences ->
            preferences[LETTER_TYPE_KEY] = letterType
        }
    }
}