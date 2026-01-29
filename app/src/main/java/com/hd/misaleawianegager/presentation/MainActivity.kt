package com.hd.misaleawianegager.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.presentation.component.setting.SettingScreen
import com.hd.misaleawianegager.presentation.component.setting.SettingViewModel
import com.hd.misaleawianegager.presentation.theme.MisaleawiAnegagerTheme
import com.hd.misaleawianegager.utils.compose.LifeCycleObserver
import com.hd.misaleawianegager.utils.compose.favList
import com.hd.misaleawianegager.utils.compose.recentList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}

   private lateinit var splashScreen: SplashScreen

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen = installSplashScreen()
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {

            var keepSP by remember { mutableStateOf(true) }
            val viewModel = hiltViewModel<SettingViewModel>()
            val fontSize = viewModel.fontSize.collectAsStateWithLifecycle()
            val letterSpace = viewModel.letterSpace.collectAsStateWithLifecycle()
            val lineHeight = viewModel.letterHeight.collectAsStateWithLifecycle()
            val theme = viewModel.theme.collectAsStateWithLifecycle()
            val font = viewModel.font.collectAsStateWithLifecycle()
            val letterType = viewModel.letterType.collectAsStateWithLifecycle()
            val onboardShown = viewModel.boardingShown.collectAsStateWithLifecycle()
            val showBtmBarInDetail = viewModel.showBtmBarInDetail.collectAsStateWithLifecycle()



            LaunchedEffect(Unit) {
                delay(1000L)
                keepSP = false
            }

           splashScreen.setKeepOnScreenCondition {
               keepSP
           }

            var showSplashScreen by rememberSaveable  { mutableStateOf(Build.VERSION.SDK_INT < Build.VERSION_CODES.S ) }

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
              letterType =  letterTypeLatest,
              onboardShown = onboardShown,
              bottomBarShownDetails = showBtmBarInDetail
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
    letterType: String,
    onboardShown: State<Boolean>,
    bottomBarShownDetails: State<Boolean>
             ) {

    val viewModel = hiltViewModel<MainViewModel>()
    val showOthers = remember { mutableStateOf(true) }
   val showModalBottomSheet = remember{ mutableStateOf(false) }
    val showBottomBar = remember { mutableStateOf(false) }
    val startDestination = if(onboardShown.value) MisaleScreen.Home.route else MisaleScreen.Onboarding.route

    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()

    LaunchedEffect(currentBackStackEntry?.destination) {
        val route = currentBackStackEntry?.destination?.route
        showOthers.value = route == "ዋና"  || route == "ምርጥ" || route == "የቅርብ" || route == "ፈልግ"
        showBottomBar.value = route == "ዋና"  || route == "ምርጥ" || route == "የቅርብ" || route == "ፈልግ" || (bottomBarShownDetails.value && route != "onboard")
    }



    Scaffold(bottomBar = { MisaleBottomAppBar(navController = navHostController, showModalBottomSheet, showBottomBar)},  modifier = Modifier.statusBarsPadding().navigationBarsPadding()) {

        MisaleBodyContent(navHostController = navHostController, modifier = Modifier.padding(it), letterType, onEvent, startDestination, bottomBarShownDetails)

        if(showModalBottomSheet.value){
            SettingScreen(
                showModalBottomSheet,
                onEvent = onEvent,
                theme = theme,
                font = font,
                showOthers = showOthers,
                showBtmBarInDetail = bottomBarShownDetails,
            )
             }
           }

    LifeCycleObserver(
        onStart = {
            viewModel.readFavList(favList)
            viewModel.readRecentList(recentList)
        },
        onPause = {
            viewModel.writeFavList(favList)
            viewModel.writeRecentList(recentList)
                  },
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
fun MisaleBottomAppBar(
    navController: NavController,
    showModalBottomSheet: MutableState<Boolean>,
    showBottomBar: MutableState<Boolean>
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    AnimatedVisibility(
        visible = showBottomBar.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomAppBar(
            modifier = Modifier
                .shadow(
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    elevation = 4.dp
                )
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
                .height(56.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DataProvider.icons.forEach { (key, icon) ->
                    val isSelected = currentRoute == key
                    val alpha = if (isSelected) 1f else 0.4f
                    val tintColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

                    IconButton(
                        onClick = {
                            if (key == "ማስቴካክያ") {
                                showModalBottomSheet.value = !showModalBottomSheet.value
                            } else if (currentRoute != key) {
                                navController.navigate(key) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = false
                                    }
                                    launchSingleTop = true
                                    restoreState = false
                                }
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .alpha(alpha)
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
                                    imageVector = icon,
                                    contentDescription = key,
                                    tint = tintColor
                                )
                                Text(
                                    text = key.uppercase(),
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = tintColor
                                )
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
    val showModalBottomSheet = remember { mutableStateOf(false) }
    val showBottombar = remember { mutableStateOf(true) }
    MisaleBottomAppBar(navHostController, showModalBottomSheet,showBottombar )
}



