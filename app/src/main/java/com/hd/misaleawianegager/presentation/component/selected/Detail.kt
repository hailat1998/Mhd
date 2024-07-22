package com.hd.misaleawianegager.presentation.component.selected

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hd.misaleawianegager.utils.favList

@Composable
fun Selected(text: String, from: String , toDest: (s : String) -> Unit) {
    var listContains by remember { mutableStateOf(favList.contains(text)) }
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxSize()
        .clickable { toDest.invoke(from) }) {

        Column {
            if (listContains) {
                Icon(Icons.Sharp.Favorite, null,
                    modifier = Modifier.clickable {
                        listContains = false
                        favList.remove(text)
                    })
            } else {
                Icon(Icons.Sharp.FavoriteBorder, null,
                    modifier = Modifier.clickable {
                        listContains = true
                        favList.add(text)
                    })
            }

            Card() {

                Text(text = text)

            }
        }
    }
}


@Composable
@Preview
fun SD(){
   // Selected(value = "THIS IS MY STORY OF LEAVING GOOD LIFE HERE IN ETHIOPIA")
}