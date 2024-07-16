package com.hd.misaleawianegager.presentation.component.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeContent(homeState: HomeState){

    Scaffold { it ->
        Box(modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.Center) {
            if (homeState.loading) {
                CircularProgressIndicator()
            }else{
                LazyColumn {
                  items(homeState.list, {item -> item}){ it ->
                     Text(text = it)
                  }
                }
            }

        }
    }

}