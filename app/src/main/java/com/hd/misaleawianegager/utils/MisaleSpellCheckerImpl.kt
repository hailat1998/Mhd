package com.hd.misaleawianegager.utils

import android.content.Context
import android.view.textservice.SentenceSuggestionsInfo
import android.view.textservice.SpellCheckerSession
import android.view.textservice.SuggestionsInfo
import android.view.textservice.TextInfo
import android.view.textservice.TextServicesManager
import javax.inject.Singleton


@Singleton
class MisaleSpellCheckerImpl(
    private val context: Context
) : SpellCheckerSession.SpellCheckerSessionListener, MisaleSpellChecker {

    private var spellCheckerSession: SpellCheckerSession? = null
    private var wordCheckCallback: ((Boolean) -> Unit)? = null

    /**
     * Initialize the spell checker session
     */
    private fun startSpellCheck() {
        val tsm = context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager
        spellCheckerSession = tsm.newSpellCheckerSession(null, null, this, true)
    }

    /**
     * Check if a word is a valid English word
     *
     * @param word The word to check
     * @param callback Callback that will be called with the result (true if word is valid, false otherwise)
     */
    override fun checkWord(word: String, callback: (Boolean) -> Unit) {
        if (spellCheckerSession == null) {
            startSpellCheck()
        }

        wordCheckCallback = callback
        spellCheckerSession?.getSuggestions(TextInfo(word), 5)

    }

    /**
     * Close the spell checker session when no longer needed
     */
    override fun closeSession() {

        spellCheckerSession?.close()
        spellCheckerSession = null

    }

    override fun onGetSuggestions(results: Array<out SuggestionsInfo>?) {
        if (results == null || results.isEmpty()) {
            wordCheckCallback?.invoke(false)
            return
        }

        val suggestionsInfo = results[0]

        // Check if the word is valid based on the suggestions attributes
        // SuggestionsInfo.RESULT_ATTR_IN_THE_DICTIONARY indicates the word is in the dictionary
        val isValidWord = (suggestionsInfo.suggestionsAttributes and SuggestionsInfo.RESULT_ATTR_IN_THE_DICTIONARY) != 0

        wordCheckCallback?.invoke(isValidWord)
    }

    override fun onGetSentenceSuggestions(results: Array<out SentenceSuggestionsInfo>?) {
        // This method is not used for single word checking
        // But we need to implement it as part of the interface
    }
}