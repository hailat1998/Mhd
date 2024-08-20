package com.hd.misaleawianegager.presentation.component.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.utils.compose.TextCard
import kotlinx.coroutines.delay
import com.hd.misaleawianegager.R

@Composable
fun HomeContent(homeData: State<List<String>>,
                onHomeEvent: (HomeEvent) -> Unit,
                onSettingEvent: (SettingEvent) -> Unit,
                scrollIndex: State<Int>,
                toDetail: ( from: String, text: String, first: String) -> Unit,
                  ) {

    val openDialog = remember { mutableStateOf(false)    }

    val showBottomSheet = remember { mutableStateOf(false) }

    val lazyListState =rememberLazyListState(initialFirstVisibleItemIndex = scrollIndex.value)

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { index ->
               onHomeEvent.invoke(HomeEvent.ScrollPos(index))
            }
    }
    LaunchedEffect(true) {
        delay(200L)
        Log.i("HOME", "${scrollIndex.value}")
    }

    var floatLetter by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                actions = { Icon(Icons.Default.Info , null,  modifier = Modifier
                    .clickable {
                        openDialog.value = true
                    }
                    .padding(end = 20.dp)
                  )
                }
             )
        }  ,
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet.value = true }) {

                Text(floatLetter, style = MaterialTheme.typography.titleLarge)

            }
        }
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (homeData.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                val arg3 = if (homeData.value[0][0].toString() == "ኃ") "ኀ"
                else if (homeData.value[0][0].toString() == "ጳ") "ጰ"
                else homeData.value[0][0].toString()

                floatLetter = arg3
               val list = homeData.value.distinct()
                LazyColumn(state = lazyListState) {
                    items(list, { item -> item }) { it ->
                        TextCard(item = it, from = "home", first = arg3, toDetail = toDetail)
                    }
                }
            }
            if (showBottomSheet.value) {
                LaunchedEffect(Unit) {
                    lazyListState.scrollToItem(0)
                }
                HomeBottomSheet(dismissReq = showBottomSheet, onHomeEvent, onSettingEvent)
            }
            if(openDialog.value){
                AppInfoDialog(openDialog = openDialog)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeBottomSheet(dismissReq : MutableState<Boolean>,
                  loadLetter: (HomeEvent) -> Unit,
                  onSettingEvent: (SettingEvent) -> Unit) {
    ModalBottomSheet(onDismissRequest = {
        dismissReq.value = !dismissReq.value
    },
        dragHandle = null) {
        Box(modifier = Modifier.heightIn( max= 250.dp)){
        Image(painterResource(id = R.drawable.drawing_dun), null,
        contentScale = ContentScale.FillBounds)
        FlowRow(modifier = Modifier.padding(8.dp)
            .background(Color.Transparent)) {
         DataProvider.letterMap.keys.forEach { it ->
             Chip(onClick = {
                 onSettingEvent.invoke(SettingEvent.LetterType(DataProvider.letterMap[it]!!))
                 loadLetter.invoke(HomeEvent.LoadLetter(DataProvider.letterMap[it]!!))
                 dismissReq.value = !dismissReq.value },
                 colors = ChipDefaults.chipColors(backgroundColor =MaterialTheme.colorScheme.background),
                 border = BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceContainer)
             ){
                 Text(text = it, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
             }
           }
        }
    }
 }
}
@Composable
fun AppInfoDialog(openDialog: MutableState<Boolean>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = {
                    Text(text = "App Information")
                },
                text = {
                    SelectionContainer {
                    Column {
                            Text(text = "Version: 1.0.0")
                            Text(text = "Developer: Haile Temesgen")
                            Text(text = "Email: htemesgen400@gmail.com")
                            Text(text = "Telegram: @varargs1")
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { openDialog.value = false }) {
                        Text("OK", color = Color.Black)
                    }
                }
            )
        }
    }




