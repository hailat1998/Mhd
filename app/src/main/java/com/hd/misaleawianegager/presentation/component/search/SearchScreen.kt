package com.hd.misaleawianegager.presentation.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.utils.compose.TextCardAnnotated

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(list: State<List<String>>, from : String,
                 search: (query: String) -> Unit,
                 toDest: (from: String) -> Unit,
                 toDetail: (from: String, text: String, first:String) -> Unit){
    val lazyListState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }
    var query by remember { mutableStateOf("") }
    var k = 0
    var j = 0
    var qu = 0
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                                    },
                    placeholder = { Text("Search...", style= MaterialTheme.typography.labelSmall) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                  keyboardActions = KeyboardActions(onSearch = {if(query.isNotEmpty()){
                      qu = query.length
                      search.invoke(query)
                     }
                   }
                  ),

                    shape = RoundedCornerShape(20.dp),

                    leadingIcon = { Icon(Icons.Default.Search, null) }
                )
            },
            navigationIcon = {
                IconButton(onClick = { toDest.invoke(from) }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
               val list2 = list.value.distinct()
        LazyColumn(modifier = Modifier.padding(16.dp),
          state = lazyListState) {
            itemsIndexed(list2, key = { _, item -> item }) { _, item ->

                j = item.indexOf(query)
                k =  j + qu

                val annotatedString = buildAnnotatedString {
                    append(item)

                    // Apply bold style to the word "Hello"
                    addStyle(
                        style = SpanStyle(
                            background = Color.Green
                        ),
                        start = j,
                        end = k
                    )
                }
               TextCardAnnotated(item = annotatedString, from = "search", first = "  " , toDetail = toDetail)
            }
        }
    }
}