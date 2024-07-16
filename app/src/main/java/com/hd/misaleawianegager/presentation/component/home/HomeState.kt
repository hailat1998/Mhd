package com.hd.misaleawianegager.presentation.component.home

data class HomeState(
    val list: MutableList<String> = mutableListOf() ,
    var loading: Boolean = false
)