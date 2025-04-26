package com.hd.misaleawianegager.utils

interface MisaleSpellChecker {
    fun checkWord(word: String, callback: (Boolean) -> Unit)
    fun closeSession()
}