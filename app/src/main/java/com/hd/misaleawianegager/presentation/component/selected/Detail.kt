package com.hd.misaleawianegager.presentation.component.selected

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.utils.compose.favList
import com.hd.misaleawianegager.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Selected(list : State<List<String>> = mutableStateOf(emptyList()), text: String,
             from: String, toDest: (s : String) -> Unit) {
    Scaffold(topBar =  {
        TopAppBar(
            title = {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.headlineMedium
                )
            },
            backgroundColor = Color.DarkGray
        )
    } ) { it ->
        Box(modifier = Modifier.padding(it)
            .fillMaxSize()) {
            when (from) {
                "fav", "home", "recent" -> {
                    if (list.value.isEmpty()) {
                        CircularProgressIndicator()
                    } else {
                        val pagerState = rememberPagerState(
                            initialPage = 0,
                            0f
                        ) { list.value.size }
                        val index = remember { list.value.indexOf(text) }
                        LaunchedEffect(Unit) {
                            pagerState.scrollToPage(index)
                        }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            ItemText(item = list.value[page], toDest = toDest, from = from)
                        }
                    }
                }
                else -> {
                    ItemText(item = text, toDest = toDest, from = from)
                }
            }
        }
    }
}


@Composable
fun ItemText(item: String, toDest: (s : String) -> Unit, from: String){
    var listContains by remember { mutableStateOf(favList.contains(item)) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxSize()
        .clickable { toDest.invoke(from) }) {

        Column {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                if (listContains) {
                    Icon(Icons.Sharp.Favorite, null,
                        modifier = Modifier
                            .clickable {
                                listContains = false
                                favList.remove(item)
                            }
                            .padding(20.dp)
                    )
                } else {
                    Icon(Icons.Sharp.FavoriteBorder, null,
                        modifier = Modifier
                            .clickable {
                                listContains = true
                                favList.add(item)
                            }
                            .padding(20.dp)
                    )
                }
                Icon(Icons.Default.Share , null, modifier = Modifier
                    .clickable {
                        val shareText = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, item)
                        }
                        val chooserIntent = Intent.createChooser(shareText, "Misaleawi Anegager")
                        context.startActivity(chooserIntent)
                    }
                    .padding(20.dp))
                Icon(painterResource(id = R.drawable.baseline_content_copy_24) , null, modifier = Modifier
                    .clickable {
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.None)) {
                                append(item)
                            }
                        }
                        clipboardManager.setText(annotatedString)
                    }
                    .padding(20.dp)
                )
            }
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                modifier = Modifier.fillMaxWidth()) {

                Text(text = item,
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp, end= 10.dp, bottom = 10.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Composable
@Preview
fun SD(){
   // Selected(value = "THIS IS MY STORY OF LEAVING GOOD LIFE HERE IN ETHIOPIA")
}