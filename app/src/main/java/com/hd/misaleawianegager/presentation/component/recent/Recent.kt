package com.hd.misaleawianegager.presentation.component.recent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.hd.misaleawianegager.utils.compose.TextCard

@Composable
fun Recent(recentData: State<List<String>>,
           toDetail: (from: String, text: String, first: String) -> Unit,
           scrollIndex: State<Int>,
           setScroll: (Int) -> Unit){

    val lazyListState =rememberLazyListState(initialFirstVisibleItemIndex = scrollIndex.value)

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { index ->
                setScroll.invoke(index)
            }
    }
    Scaffold(topBar =  {
        TopAppBar(
            title = {
                Text(
                    text = "Recent",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            backgroundColor = Color.DarkGray
        )
    }) { it ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            if (recentData.value.isEmpty()) {
                CircularProgressIndicator()
            }else{
                val list = recentData.value.distinct()
                LazyColumn(state = lazyListState) {
                    items(list, {item -> item}){ it ->
                       TextCard(item = it, from = "recent", first = " " , toDetail = toDetail)
                    }
                }
            }
        }
    }
}