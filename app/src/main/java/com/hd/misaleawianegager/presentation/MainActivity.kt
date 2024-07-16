package com.hd.misaleawianegager.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MisaleawiAnegagerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
          MisaleApp(navHostController = navHostController)
                }
            }
        }
    }
}



@Composable
fun MisaleApp(navHostController: NavHostController){
    Scaffold(bottomBar = { MisaleBottomAppBar(navController = navHostController)} ) {
        MisaleBodyContent(navHostController = navHostController, modifier = Modifier.padding(it))
    }
}


@Composable
fun MisaleBottomAppBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier.height(56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DataProvider.icons.keys.forEach { key ->
                IconButton(onClick = { /* Handle navigation or action here */ }) {
                    Icon(imageVector = DataProvider.icons[key]!!, contentDescription = null)
                }
            }
        }
    }
}

@Preview
@Composable
fun S(){
    val navHostController = rememberNavController()
    MisaleApp(navHostController = navHostController)
}



