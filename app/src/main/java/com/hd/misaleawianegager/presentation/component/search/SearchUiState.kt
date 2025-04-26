package com.hd.misaleawianegager.presentation.component.search

data class SearchUiState(
    val isLoading: Boolean = false,
    val word: String? = null,
    val error: String? = null
)

