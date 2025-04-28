package com.hd.misaleawianegager.utils


interface MisaleSpellChecker {
    suspend fun checkWord(word: String): Boolean
    fun closeSession()
}
