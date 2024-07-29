package com.hd.misaleawianegager.presentation.component.fav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.hd.misaleawianegager.utils.compose.TextCard

@Composable
fun FavScreen(favList: List<String> , toDetail:(from: String, s: String, first: String) -> Unit){
    val lazyListState = rememberLazyListState()
    Scaffold(topBar =  {
        TopAppBar(
            title = {
                Text(
                    text = "Favourite",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            backgroundColor = Color.DarkGray
        )
    }) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()){
            val list = favList.distinct()
            LazyColumn(state = lazyListState) {
                items(list, {item -> item}){ text ->
                   TextCard(item = text, from = "fav", first = " ", toDetail = toDetail)
                }
            }
        }
    }
}