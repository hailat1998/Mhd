package com.hd.misaleawianegager.presentation.component.recent

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.utils.compose.TextCard
import kotlinx.coroutines.delay

@Composable
fun Recent(recentData: State<List<String>>,
           toDetail: (from: String, text: String, first: String) -> Unit,
           scrollIndex: State<Int>,
           setScroll: (Int) -> Unit
           ){

    val lazyListState =rememberLazyListState(initialFirstVisibleItemIndex = scrollIndex.value)

    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { index ->
                setScroll.invoke(index)
            }
    }

    LaunchedEffect(Unit) {
        delay(1000L)
        loading = false
    }
    Scaffold(topBar =  {
        TopAppBar(
            title = {
                Text(
                    text = "የቅርብ",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.height(45.dp)
        )
    }) { it ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (loading && recentData.value.isEmpty()) {
                CircularProgressIndicator()
            }else{
                val list = recentData.value.distinct().reversed()
                LazyColumn(state = lazyListState, modifier = Modifier.padding(8.dp)) {
                    items(list, {item -> item}){
                       TextCard(item = it, from = "የቅርብ", first = " " , toDetail = toDetail)
                    }
                }
            }
        }
    }
}