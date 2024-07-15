package com.hd.misaleawianegager.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.hd.misaleawianegager.R

object DataProvider {
    val icons = mapOf(
        "recent" to Icons.Rounded.Refresh,
        "fav" to Icons.Rounded.Favorite,
        "home" to Icons.Rounded.Home,
        "search" to Icons.Rounded.Search,
        "setting" to Icons.Rounded.Settings,
    )

}