package com.hd.misaleawianegager.presentation.component.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(){

    Scaffold(topBar = { TopAppBar(title = { Text(text = "Setting")})}) {it ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(it).fillMaxSize()){
            Text(text = "ችግር ነው ጌትነት፣ በቅቤ ያስበላል." , style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Preview
@Composable
fun Sf(){
    MisaleawiAnegagerTheme {
        SettingScreen()
    }

}