package com.hd.misaleawianegager.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
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


            val fontSize = viewModel.fontSize.collectAsStateWithLifecycle().value
            val letterSpace = viewModel.letterSpace.collectAsStateWithLifecycle().value
            val lineHeight = viewModel.letterHeight.collectAsStateWithLifecycle().value
            val theme = viewModel.theme.collectAsStateWithLifecycle()
            val font = viewModel.font.collectAsStateWithLifecycle()


            MisaleawiAnegagerTheme(
                fontSize = fontSize,
                letterSpace = letterSpace,
                letterHeight = lineHeight,
            ) {
                val navHostController = rememberNavController()
          MisaleApp(
              navHostController = navHostController,
              onEvent = viewModel::onEvent,
              theme = theme,
              font = font,
              )
                }
            }
        }

}



@Composable
fun MisaleApp(
    navHostController: NavHostController, onEvent: (SettingEvent) -> Unit,
    theme: State<String?>,
    font: State<Int?>
             ){
   val showModalBottomSheet = remember{ mutableStateOf(false) }
    Scaffold(bottomBar = { MisaleBottomAppBar(navController = navHostController, showModalBottomSheet)} ) {
        MisaleBodyContent(navHostController = navHostController, modifier = Modifier.padding(it))
        if(showModalBottomSheet.value){
            SettingScreen(showModalBottomSheet,
                onEvent = onEvent,
                theme = theme,
                font = font
                )
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
    //MisaleApp(navHostController = navHostController)
}



