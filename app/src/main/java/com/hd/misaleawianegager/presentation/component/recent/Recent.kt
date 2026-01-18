package com.hd.misaleawianegager.presentation.component.recent

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.utils.compose.TextCard
import kotlinx.coroutines.delay
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recent(
    recentData: State<List<String>>,
    toDetail: (from: String, text: String, first: String) -> Unit,
    scrollIndex: State<Int>,
    setScroll: (Int) -> Unit,
    drag: () -> Unit
) {

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = scrollIndex.value
    )


    var loading by remember { mutableStateOf(true) }


    LaunchedEffect(Unit) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { index ->
                setScroll(index)
            }
    }
    var xAmount by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(xAmount) {
        if (xAmount < 0f && abs(xAmount) > 10f) {
            drag.invoke()
        }
    }

    LaunchedEffect(Unit) {
        delay(1000L)
        loading = false
    }

    val items by recentData

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "የቅርብ",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.height(45.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(Unit) {
                    var xAmountLocal = 0f
                    detectDragGestures(onDragEnd = { xAmount = xAmountLocal } ) { _, dragAmount ->
                        xAmountLocal = dragAmount.x
                    }
                }
            ,
            contentAlignment = Alignment.Center
        ) {
            if (loading && items.isEmpty()) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(
                        items = items,
                        key = { item -> item.hashCode() }
                    ) { item ->

                        key(item) {
                            TextCard(
                                item = item,
                                from = "የቅርብ",
                                first = " ",
                                toDetail = toDetail
                            )
                        }
                    }
                }
            }
        }
    }
}