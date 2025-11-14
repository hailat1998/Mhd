package com.hd.misaleawianegager.presentation.component.home

sealed class HomeEvent {
    data class LoadLetter(val value : String): HomeEvent()
    data class WriteText(val text: String): HomeEvent()
    data class ScrollPos(val value: Int): HomeEvent()
}