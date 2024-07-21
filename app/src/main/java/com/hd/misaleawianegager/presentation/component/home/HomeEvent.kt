package com.hd.misaleawianegager.presentation.component.home

sealed class HomeEvent {
    data class LoadLetter(val value : String): HomeEvent()
}