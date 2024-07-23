package com.hd.misaleawianegager.presentation.component.fav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
fun FavScreen(favList: List<String> , toDetail:(from: String, s: String) -> Unit){
    Scaffold {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            val list = favList.distinct()
            LazyColumn {
                items(list, {item -> item}){ text ->
                    Text(text = text )
                }
            }
        }
    }
}