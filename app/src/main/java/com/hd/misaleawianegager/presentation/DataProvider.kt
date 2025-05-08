package com.hd.misaleawianegager.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings

object DataProvider {
    val icons = mapOf(
        "የቅርብ" to Icons.Rounded.Refresh,
        "ምርጥ" to Icons.Rounded.Favorite,
        "ዋና" to Icons.Rounded.Home,
        "ፈልግ" to Icons.Rounded.Search,
        "ማስቴካክያ" to Icons.Rounded.Settings,
    )

    val letterMap = mapOf(
        "ሀ" to "01Ha.txt",
        "ለ" to "02Le.txt",
        "ሐ" to "03Ha.txt",
        "መ" to "04Me.txt",
        "ሠ" to "05Se.txt",
        "ረ" to "06Re.txt",
        "ሰ" to "07Se.txt",
        "ሸ" to "08She.txt",
        "ቀ" to "09Qe.txt",
        "በ" to "10Be.txt",
        "ተ" to "11Te.txt",
        "ቸ" to "12Che.txt",
        "ኀ" to "13Ha.txt",
        "ነ" to "14Ne.txt",
        "አ" to "16A.txt",
        "ከ" to "17Ke.txt",
        "ወ" to "19We.txt",
        "ዐ" to "20A.txt",
        "ዘ" to "21Ze.txt",
        "የ" to "23Ye.txt",
        "ደ" to "24De.txt",
        "ጀ" to "25Je.txt",
        "ገ" to "26Ge.txt",
        "ጠ" to "27Xe.txt",
        "ጨ" to "28Che.txt",
        "ጰ" to "29Pe.txt",
        "ጸ" to "30Xe.txt",
        "ፀ" to "31Xe.txt",
        "ፈ" to "32Fe.txt"
    )


    val orderMapNav = mapOf(
        MisaleScreen.Recent.route to 0,
        MisaleScreen.Fav.route to 1,
        MisaleScreen.Home.route to 2,
        MisaleScreen.Search.route to 3,
        MisaleScreen.Detail.route to 4,
        MisaleScreen.Onboarding.route to 5
    )

}