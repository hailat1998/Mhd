package com.hd.misaleawianegager.presentation.component.fav

sealed class FavEvent {
    data object ReadFav: FavEvent()
    data object WriteFile: FavEvent()
}