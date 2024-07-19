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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hd.misaleawianegager.presentation.component.setting.SettingScreen
import com.hd.misaleawianegager.presentation.component.setting.SettingViewModel
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = hiltViewModel<SettingViewModel>()
            MisaleawiAnegagerTheme {
                val navHostController = rememberNavController()
          MisaleApp(navHostController = navHostController)
                }
            }
        }

}



@Composable
fun MisaleApp(navHostController: NavHostController){
   val showModalBottomSheet = remember{ mutableStateOf(false) }
    Scaffold(bottomBar = { MisaleBottomAppBar(navController = navHostController, showModalBottomSheet)} ) {
        MisaleBodyContent(navHostController = navHostController, modifier = Modifier.padding(it))
        if(showModalBottomSheet.value){
            SettingScreen(showModalBottomSheet)
        }
    }
}


@Composable
fun MisaleBottomAppBar(navController: NavController, showModalBottomSheet: MutableState<Boolean>) {
    BottomAppBar(
        modifier = Modifier.height(56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DataProvider.icons.keys.forEach { key ->
                IconButton(onClick = {
                    if(key == "setting"){
                       showModalBottomSheet.value = !showModalBottomSheet.value
                    }else{
                        navController.navigate(key)
                    }
                }) {
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



