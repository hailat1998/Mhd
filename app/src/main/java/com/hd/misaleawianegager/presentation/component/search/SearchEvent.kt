package com.hd.misaleawianegager.presentation.component.search

sealed class SearchEvent {
    data class SearchProverb(val query: String): SearchEvent()
    data class ConvertWord(val word: String): SearchEvent()
    data object LoadSingle: SearchEvent()
}