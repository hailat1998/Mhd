package com.hd.misaleawianegager.presentation.component.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import kotlinx.coroutines.delay

@Composable
fun HomeContent(homeData: State<List<String>>,
                loadLetter: (HomeEvent) -> Unit,
                onEvent: (SettingEvent) -> Unit,
                toDetail: ( from: String, text: String, first: String) -> Unit,
                  ){
val showBottomSheet = remember{ mutableStateOf( false ) }


    Scaffold(
        topBar = { TopAppBar(title = {Text(text = "Home" ,
            style = MaterialTheme.typography.headlineMedium) },
            backgroundColor = Color.DarkGray)},
        floatingActionButton = {
        FloatingActionButton(onClick = { showBottomSheet.value = true }) {
            Icon(Icons.Default.Add, null)
        }
    }) { it ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.Center) {
            if (homeData.value.isEmpty()) {
                CircularProgressIndicator()
            }else{
                LazyColumn {
                  items(homeData.value, {item -> item}){ it ->
                     Text(text = it,
                         modifier = Modifier.clickable{ toDetail.invoke( "home" , it,
                             if(homeData.value[0][0].toString() == "ኃ") "ኀ"
                         else if(homeData.value[0][0].toString() == "ጳ") "ጰ"
                         else homeData.value[0][0].toString())})
                  }
                }
            }
        }
        if(showBottomSheet.value){
            HomeBootSheet(dismissReq = showBottomSheet , loadLetter, onEvent)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeBootSheet(dismissReq : MutableState<Boolean>,
                  loadLetter: (HomeEvent) -> Unit,
                  onEvent: (SettingEvent) -> Unit) {
    ModalBottomSheet(onDismissRequest = {
        dismissReq.value = !dismissReq.value
    }) {
        FlowRow(modifier = Modifier.padding(8.dp)) {
         DataProvider.letterMap.keys.forEach { it ->
             Chip(onClick = {
                 onEvent.invoke(SettingEvent.LetterType(DataProvider.letterMap[it]!!))
                 loadLetter.invoke(HomeEvent.LoadLetter(DataProvider.letterMap[it]!!))
                 dismissReq.value = !dismissReq.value },
                 colors = ChipDefaults.chipColors(backgroundColor =MaterialTheme.colorScheme.background),
                 border = BorderStroke(2.dp, Color.DarkGray)
             ){
                 Text(text = it, style = MaterialTheme.typography.headlineSmall)
             }
           }
        }
    }
}