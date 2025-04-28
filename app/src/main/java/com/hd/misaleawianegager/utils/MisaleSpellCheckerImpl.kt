package com.hd.misaleawianegager.utils

import android.content.Context
import android.view.textservice.SentenceSuggestionsInfo
import android.view.textservice.SpellCheckerSession
import android.view.textservice.SuggestionsInfo
import android.view.textservice.TextInfo
import android.view.textservice.TextServicesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume


@Singleton
class MisaleSpellCheckerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SpellCheckerSession.SpellCheckerSessionListener, MisaleSpellChecker {

    private var spellCheckerSession: SpellCheckerSession? = null
    private val mainDispatcher = Dispatchers.Main
    private val mainScope = CoroutineScope(mainDispatcher)

    /**
     * Initialize the spell checker session
     */
    private suspend fun startSpellCheck() {
        withContext(mainDispatcher) {
            val tsm = context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager
            spellCheckerSession = tsm.newSpellCheckerSession(null, null, this@MisaleSpellCheckerImpl, true)
        }
    }

    /**
     * Check if a word is a valid English word
     *
     * @param word The word to check
     * @return true if word is valid, false otherwise
     */
    override suspend fun checkWord(word: String): Boolean = suspendCancellableCoroutine { continuation ->
        if (spellCheckerSession == null) {
            mainScope.launch {
                startSpellCheck()
                checkWordInternal(word, continuation)
            }
        } else {
            checkWordInternal(word, continuation)
        }
    }

    private fun checkWordInternal(word: String, continuation: CancellableContinuation<Boolean>) {
        val listener = object : SpellCheckerSession.SpellCheckerSessionListener {
            override fun onGetSuggestions(results: Array<out SuggestionsInfo>?) {
                if (results == null || results.isEmpty()) {
                    continuation.resume(false)
                    return
                }

                val suggestionsInfo = results[0]
                val isValidWord = (suggestionsInfo.suggestionsAttributes and SuggestionsInfo.RESULT_ATTR_IN_THE_DICTIONARY) != 0
                continuation.resume(isValidWord)
            }

            override fun onGetSentenceSuggestions(results: Array<out SentenceSuggestionsInfo>?) {
                // Not used
            }
        }

        mainScope.launch {
            spellCheckerSession?.getSuggestions(TextInfo(word), 5)
        }
    }

    /**
     * Close the spell checker session when no longer needed
     */
    override fun closeSession() {
        mainScope.launch {
            spellCheckerSession?.close()
            spellCheckerSession = null
        }
    }

    override fun onGetSuggestions(results: Array<out SuggestionsInfo>?) {
        // This will be handled by our temporary listener
    }

    override fun onGetSentenceSuggestions(results: Array<out SentenceSuggestionsInfo>?) {
        // Not used
    }
}