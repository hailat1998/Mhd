package com.hd.misaleawianegager.presentation.component.home

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.presentation.DataProvider

@Composable
fun HomeContent(homeData: MutableList<String>){
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
            if (homeData.isEmpty()) {
                CircularProgressIndicator()
            }else{
                LazyColumn {
                  items(homeData, {item -> item}){ it ->
                     Text(text = it)
                  }
                }
            }
        }
        if(showBottomSheet.value){
            HomeBootSheet(dismissReq = showBottomSheet)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeBootSheet(dismissReq : MutableState<Boolean>) {
    ModalBottomSheet(onDismissRequest = {
        dismissReq.value = !dismissReq.value
    }) {
        FlowRow(modifier = Modifier.padding(8.dp)) {
         DataProvider.letterMap.keys.forEach { it ->
             Chip(onClick = { dismissReq.value = !dismissReq.value },
                 colors = ChipDefaults.chipColors(backgroundColor = Color.Black)){
                 Text(text = it)
             }
         }
        }
    }
}