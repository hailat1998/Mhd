package com.hd.misaleawianegager.presentation

sealed class MisaleScreen(val route : String) {
    data object Home:MisaleScreen("ዋና")
    data object Fav: MisaleScreen("ምርጥ")
    data object Recent: MisaleScreen("የቅርብ")
    data object Search: MisaleScreen("ፈልግ")
    data object Detail: MisaleScreen("selected")
    data object Onboarding: MisaleScreen("onboard")
}