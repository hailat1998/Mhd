package com.hd.misaleawianegager.presentation.component.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.utils.compose.TextCardAnnotated
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    list: State<List<String>>,
    from : String,
    wordFlow: StateFlow<SearchUiState>,
    onSearchEvent: (s: SearchEvent) -> Unit,
    toDest: (from: String) -> Unit,
    toDetail: (from: String, text: String, first:String) -> Unit,
                ) {

    val word = viewModel.wordResult.collectAsStateWithLifecycle()
    
    val lazyListState = rememberLazyListState()
    val focusRequester = remember { FocusRequester() }

    val localFont = FontFamily.Default
    val textStyle = TextStyle(fontFamily = localFont)

    val query = remember { mutableStateOf("") }
    var k: Int
    var j: Int

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


    val currentWord = word.value.word

    if (!word.value.isLoading && !currentWord.isNullOrEmpty()) {
        query.value = currentWord
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalTextStyle provides textStyle) {
            SearchTopBar(query, focusRequester, onSearchEvent, word)
         }

        val list2 = list.value.distinct()

        LazyColumn(modifier = Modifier.padding(16.dp),
          state = lazyListState) {
            itemsIndexed(list2, key = { _, item -> item }) { _, item ->

                j = item.indexOf(query.value)
                k =  j + query.value.length

                val annotatedString = buildAnnotatedString {
                    append(item)
                    addStyle(
                        style = SpanStyle(
                            background = Color.Green
                        ),
                        start = j,
                        end = k
                    )
                }
               TextCardAnnotated(item = annotatedString,
                   from = "search", first = "  " ,
                   toDetail = toDetail,
                  )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(query: MutableState<String>,
                 focusRequester: FocusRequester,
                 onSearchEvent: (s: SearchEvent) -> Unit,
                 word: State<SearchUiState>
                 ){

    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.5f,
                            red = 0.9f,
                            green = 0.8f,
                            blue = 0.9f
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        BasicTextField(
                            value = query.value,
                            onValueChange = { query.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = FontFamily.Default,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                baselineShift = BaselineShift.None
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    if (query.value.isNotEmpty()) {
                                        onSearchEvent.invoke(SearchEvent.SearchProverb(query.value))
                                    }
                                }
                            ),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                        )

                        if (query.value.isEmpty()) {
                            Text(
                                "Search...",
                                fontSize = 13.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    if (query.value.isNotEmpty()) {
                        IconButton(
                            onClick = { query.value = "" },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        actions = {
            if (word.value.isLoading) {
                IconButton(onClick = {}) {
                    CircularProgressIndicator(modifier = Modifier.padding(10.dp))
                }
            }else {
                IconButton(onClick = { onSearchEvent.invoke(SearchEvent.ConvertWord(query.value) )})  {
                    Icon(painterResource(R.drawable.swap_vert_24px), null)
            }
          }
            IconButton(onClick = { onSearchEvent.invoke(SearchEvent.LoadSingle ) } )  {
                Icon(painterResource(R.drawable.baseline_shuffle_24), null)
            }
        },
        modifier = Modifier.height(49.dp)
    )
}

@Preview
@Composable
fun SearchTopBarPrevview(){
    // SearchTopBar("")
}