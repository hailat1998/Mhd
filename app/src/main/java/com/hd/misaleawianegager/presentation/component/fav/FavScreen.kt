package com.hd.misaleawianegager.presentation.component.fav

import androidx.compose.animation.ExperimentalSharedTransitionApi
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavScreen(favList: State<List<String>> ,
              toDetail:(from: String, s: String, first: String, ) -> Unit,
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
                    text = "ምርጥ",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.height(48.dp)
        )
    }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            contentAlignment = Alignment.Center) {
            if (loading && favList.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                val list = favList.value.distinct()
                LazyColumn(state = lazyListState) {
                    items(list, { item -> item }) { text ->
                        if(text.isNotEmpty()){
                            TextCard(item = text, from = "ምርጥ", first = " ", toDetail = toDetail)
                        }
                    }
                }
            }
        }
    }
  }