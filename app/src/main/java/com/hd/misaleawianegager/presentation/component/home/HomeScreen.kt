package com.hd.misaleawianegager.presentation.component.home

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.presentation.DataProvider

@Composable
fun HomeContent(homeData: State<List<String>>,loadLetter: (HomeEvent) -> Unit, toDetail: ( from: String, s: String) -> Unit){
val showBottomSheet = remember{ mutableStateOf( false ) }
    Scaffold(floatingActionButton = {
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
                         modifier = Modifier.clickable{ toDetail.invoke( "home" , it)})
                  }
                }
            }
        }
        if(showBottomSheet.value){
            HomeBootSheet(dismissReq = showBottomSheet, loadLetter)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeBootSheet(dismissReq : MutableState<Boolean>, loadLetter: (HomeEvent) -> Unit) {
    ModalBottomSheet(onDismissRequest = {
        dismissReq.value = !dismissReq.value
    }) {
        FlowRow(modifier = Modifier.padding(8.dp)) {
         DataProvider.letterMap.keys.forEach { it ->
             Chip(onClick = {
                 loadLetter.invoke(HomeEvent.LoadLetter(DataProvider.letterMap[it]!!))
                 dismissReq.value = !dismissReq.value },
                 colors = ChipDefaults.chipColors(backgroundColor = Color.Black)){
                 Text(text = it)
             }
         }
        }
    }
}