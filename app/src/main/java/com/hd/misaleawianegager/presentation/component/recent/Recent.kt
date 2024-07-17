package com.hd.misaleawianegager.presentation.component.recent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Recent(recentData: MutableState<List<String>>){
    Scaffold() { it ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.Center) {
            if (recentData.value.isEmpty()) {
                CircularProgressIndicator()
            }else{
                LazyColumn {
                    items(recentData.value, {item -> item}){ it ->
                        Text(text = it)
                    }
                }
            }
        }

    }
}