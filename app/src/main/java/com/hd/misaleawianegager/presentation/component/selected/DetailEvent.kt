package com.hd.misaleawianegager.presentation.component.selected

sealed class DetailEvent {
    data object LoadRecent: DetailEvent()
    data object LoadFav: DetailEvent()
    data class LoadLetter(val q: String): DetailEvent()
    data class LoadAIContent(val proverb: String): DetailEvent()
}