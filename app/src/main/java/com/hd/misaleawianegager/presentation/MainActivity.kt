package com.hd.misaleawianegager.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import com.hd.misaleawianegager.R
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.presentation.component.setting.SettingScreen
import com.hd.misaleawianegager.presentation.component.setting.SettingViewModel
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme
import com.hd.misaleawianegager.utils.compose.LifeCycleObserver
import com.hd.misaleawianegager.utils.compose.favList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


private const val REQUEST_CODE_POST_NOTIFICATIONS = 1


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_CODE_POST_NOTIFICATIONS
            )
        }
        setContent {
            val viewModel = hiltViewModel<SettingViewModel>()


            val fontSize = viewModel.fontSize.collectAsStateWithLifecycle()
            val letterSpace = viewModel.letterSpace.collectAsStateWithLifecycle()
            val lineHeight = viewModel.letterHeight.collectAsStateWithLifecycle()
            val theme = viewModel.theme.collectAsStateWithLifecycle()
            val font = viewModel.font.collectAsStateWithLifecycle()
            val letterType = viewModel.letterType.collectAsStateWithLifecycle()

            var showSplashScreen by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {

               delay(2000L)

                showSplashScreen = false


            }

            MisaleawiAnegagerTheme(
                theme = theme.value!! ,
                fontSize = fontSize,
                selectedFont = font,
                letterSpace = letterSpace,
                letterHeight = lineHeight,
            ) {
                val navHostController = rememberNavController()
                if(showSplashScreen){
                      SplashScreen()
                }else{
                    val letterTypeLatest = letterType.value!!
          MisaleApp(
              navHostController = navHostController,
              onEvent = viewModel::onEvent,
              theme = theme,
              font = font,
              letterTypeLatest
              )
                }
            }
        }
    }
}


@Composable
fun MisaleApp(
    navHostController: NavHostController,
    onEvent: (SettingEvent) -> Unit,
    theme: State<String?>,
    font: State<String?>,
    letterType: String
             ){
    val viewModel = hiltViewModel<MainViewModel>()
   val showModalBottomSheet = remember{ mutableStateOf(false) }
    Scaffold(bottomBar = { MisaleBottomAppBar(navController = navHostController, showModalBottomSheet)} ) {
        MisaleBodyContent(navHostController = navHostController, modifier = Modifier.padding(it), letterType, onEvent)
        if(showModalBottomSheet.value){
            SettingScreen( showModalBottomSheet ,
                onEvent = onEvent,
                theme = theme,
                font = font,
                )
        }
    }

    LifeCycleObserver(
        onStart = {viewModel.readFavList(favList)},
        onPause = { viewModel.writeFavList(favList) },
        onStop = { viewModel.writeFavList(favList) },
    )
    val worker = viewModel.working.collectAsState(initial = "RUNNING")
    LifeCycleObserver(
        onStart = { Log.i("HOMEVIEWMODEL", worker.value)},
        onResume = { Log.i("HOMEVIEWMODEL", worker.value) },
        onPause = { Log.i("HOMEVIEWMODEL", worker.value) },
        onStop = { Log.i("HOMEVIEWMODEL", worker.value) }
    )
}


@Composable
fun MisaleBottomAppBar(navController: NavController,
                       showModalBottomSheet: MutableState<Boolean>) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val route = currentBackStackEntry?.destination?.route
    BottomAppBar(
        modifier = Modifier.height(56.dp)
            .shadow(shape = RoundedCornerShape(topStart = 14.dp, topEnd = 15.dp), elevation = 0.dp)
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        containerColor = MaterialTheme.colorScheme.surface,

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .shadow(shape = RoundedCornerShape(topStart = 14.dp, topEnd = 15.dp), elevation = 0.dp)
                .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DataProvider.icons.keys.forEach { key ->
                if(route == key){
                    IconButton(
                        onClick = {
                            if (key == "setting") {
                                showModalBottomSheet.value = !showModalBottomSheet.value
                            } else {
                                navController.navigate(key)
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = DataProvider.icons[key]!!,
                                    contentDescription = null
                                )
                                Text(text = key.uppercase(), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }else{
                    IconButton(
                        onClick = {
                            if (key == "setting") {
                                showModalBottomSheet.value = !showModalBottomSheet.value
                            } else {
                                navController.navigate(key)
                            }
                        },
                        modifier = Modifier
                            .alpha(0.4f)
                            .size(60.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = DataProvider.icons[key]!!,
                                    contentDescription = null
                                )
                                Text(text = key.uppercase(), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun SplashScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painterResource(id = R.drawable.bitmap) , contentDescription = null)
        Spacer(modifier = Modifier.height(200.dp))
            CircularProgressIndicator()
    }
  }
}

@Preview
@Composable
fun S(){
    val navHostController = rememberNavController()
    //MisaleApp(navHostController = navHostController)
}



