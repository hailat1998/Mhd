package com.hd.misaleawianegager.presentation.component.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.presentation.component.setting.SettingEvent
import com.hd.misaleawianegager.utils.compose.TextCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(homeData: State<List<String>>,
                onHomeEvent: (HomeEvent) -> Unit,
                onSettingEvent: (SettingEvent) -> Unit,
                scrollIndex: State<Int>,
                toDetail: ( from: String, text: String, first: String) -> Unit,
                toBoarding: () -> Unit
                  ) {

    val openDialog = remember { mutableStateOf(false)    }

    val showBottomSheet = remember { mutableStateOf(false) }

    val lazyListState =rememberLazyListState(initialFirstVisibleItemIndex = scrollIndex.value)

    var showFloatButton by remember {  mutableStateOf(true) }

    val firstVisibleItemIndex = remember { mutableIntStateOf(0) }

    val context = LocalContext.current as Activity

    BackHandler {
        context.finish()
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .collect { currentIndex ->
                onHomeEvent.invoke(HomeEvent.ScrollPos(currentIndex))
                if (lazyListState.isScrollInProgress) {
                    if (currentIndex > firstVisibleItemIndex.intValue) {
                        showFloatButton = false
                    } else {
                        delay(200)
                        showFloatButton = true
                    }
                }
                firstVisibleItemIndex.intValue = currentIndex
            }
    }

    var floatLetter by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ዋና",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { openDialog.value = true }, Modifier.padding(end = 13.dp)) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "INFO"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.height(45.dp)
            )
        },

        floatingActionButton = {
            AnimatedVisibility(
                visible = showFloatButton,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }) {
            FloatingActionButton(onClick = { showBottomSheet.value = true }, modifier = Modifier.testTag("FLOAT")
            ) {
                Text(floatLetter, style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary))
            }
          }
        }
    ) { it ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (homeData.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                val arg3 = if (homeData.value[0][0].toString() == "ኃ" )  "ኀ"
                else if (homeData.value[0][0].toString() == "ጳ")  "ጰ"
                else homeData.value[0][0].toString()

                floatLetter = arg3

               val list = homeData.value.distinct()

                LazyColumn(state = lazyListState,modifier = Modifier.padding(8.dp)) {
                    items(list, { item -> item }) { it ->
                        TextCard(item = it, from = "ዋና", first = arg3, toDetail = toDetail)
                    }
                }
            }
            if (showBottomSheet.value) {
                LaunchedEffect(Unit) {
                    lazyListState.scrollToItem(0)
                }
                HomeBottomSheet(dismissReq = showBottomSheet, onHomeEvent, onSettingEvent)
            }
            if(openDialog.value){
                AppInfoDialog(openDialog = openDialog, toBoarding = toBoarding)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeBottomSheet(dismissReq : MutableState<Boolean>,
                  loadLetter: (HomeEvent) -> Unit,
                  onSettingEvent: (SettingEvent) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = {
        dismissReq.value = !dismissReq.value
    },
        dragHandle = null
    ) {
        Box(modifier = Modifier.heightIn( max= 250.dp)){

        Image(painterResource(id = R.drawable.drawing_dun), null,

        contentScale = ContentScale.FillBounds)

        FlowRow(modifier = Modifier
            .padding(8.dp)
            .background(Color.Transparent)
        ) {
         DataProvider.letterMap.keys.forEach { it ->

             Chip(onClick = {

                 onSettingEvent.invoke(SettingEvent.LetterType(DataProvider.letterMap[it]!!))

                 loadLetter.invoke(HomeEvent.LoadLetter(DataProvider.letterMap[it]!!))

                 dismissReq.value = !dismissReq.value

            },
                 colors = ChipDefaults.chipColors(backgroundColor =MaterialTheme.colorScheme.background),

                 border = BorderStroke(2.dp, Color(0xFFFFD700))
             ){
                 Box(modifier = Modifier.size(35.dp), contentAlignment = Alignment.Center) {
                     Text(text = it, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
                 }
             }
           }
        }
     }
  }
}

@Composable
fun AppInfoDialog(openDialog: MutableState<Boolean>, toBoarding: () -> Unit) {

        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(16.dp),
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.bitmap),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "App Information",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            text = {
                SelectionContainer {
                    Column(horizontalAlignment = Alignment.Start) {
                        InfoRow("Version:", "2.0")
                        InfoRow("Developer:", "Haile Temesgen")
                        InfoRow("Email:", "htemesgen400@gmail.com")

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "Go to Introduction",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .clickable {
                                    toBoarding.invoke()
                                    openDialog.value = false
                                }
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            },
            confirmButton = { },
            dismissButton = {
                OutlinedButton(
                    onClick = { openDialog.value = false },
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.primary)
                    )
                ) {
                    Text(
                        "OK",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
