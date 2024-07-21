package com.hd.misaleawianegager.presentation

sealed class MisaleScreen(val route : String) {
    data object Home:MisaleScreen("home")
    data object Fav: MisaleScreen("fav")
    data object Recent: MisaleScreen("recent")
    data object Search: MisaleScreen("search")
    data object Setting: MisaleScreen("setting")
    data object Detail: MisaleScreen("selected")
}