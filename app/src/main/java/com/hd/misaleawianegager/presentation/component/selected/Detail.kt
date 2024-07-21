package com.hd.misaleawianegager.presentation.component.selected

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Selected(value: String, from: String , toDest: (s : String) -> Unit){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().clickable { toDest.invoke(from) }){
        Card(){

            Text(text = value)

        }
    }
}


@Composable
@Preview
fun SD(){
   // Selected(value = "THIS IS MY STORY OF LEAVING GOOD LIFE HERE IN ETHIOPIA")
}